package com.clara.test.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.clara.test.client.ReleaseWebClient;
import com.clara.test.dto.ArtistComparissonRequestDto;
import com.clara.test.dto.ArtistComparissonResponseDto;
import com.clara.test.dto.ArtistDiscogResponseDto;
import com.clara.test.dto.ArtistRequestDto;
import com.clara.test.dto.ArtistResponseDto;
import com.clara.test.dto.GenreDto;
import com.clara.test.dto.LabelDto;
import com.clara.test.dto.MasterResponseDto;
import com.clara.test.dto.ReleaseDto;
import com.clara.test.dto.ReleaseGenreDto;
import com.clara.test.dto.ReleaseLabelDto;
import com.clara.test.dto.ReleaseStyleDto;
import com.clara.test.dto.ResponseWrapper;
import com.clara.test.dto.ResultDto;
import com.clara.test.dto.StyleDto;
import com.clara.test.entity.Genre;
import com.clara.test.entity.Label;
import com.clara.test.entity.Release;
import com.clara.test.entity.Style;
import com.clara.test.feign.DiscogFeign;
import com.clara.test.mapper.ReleaseMapper;
import com.clara.test.service.ArtistReleaseService;
import com.clara.test.service.ArtistService;
import com.clara.test.service.DiscogService;
import com.clara.test.service.GenreService;
import com.clara.test.service.LabelService;
import com.clara.test.service.ReleaseGenreService;
import com.clara.test.service.ReleaseLabelService;
import com.clara.test.service.ReleaseService;
import com.clara.test.service.ReleaseStyleService;
import com.clara.test.service.StyleService;
import com.clara.test.utils.Webclient;

import lombok.NonNull;

@Service
public class DiscogServiceImpl implements DiscogService {
	
	@NonNull
	private DiscogFeign discogFeign;
	
	@NonNull
	private ArtistService artistService;
	
	@NonNull
	private ReleaseService releaseService;
	
	@NonNull
	private ArtistReleaseService artistReleaseService;
	
	@NonNull
	private LabelService labelService;
	
	@NonNull
	private StyleService styleService;
	
	@NonNull
	private GenreService genreService;
	
	@NonNull
	private ReleaseLabelService releaseLabelService;
	
	@NonNull
	private ReleaseGenreService releaseGenreService;
	
	@NonNull
	private ReleaseStyleService releaseStyleService;
	
	@Value("${external.api.discog.token}")
	private String discogToken;
	
	public DiscogServiceImpl(
			DiscogFeign discogFeign,
			ArtistService artistService,
			ReleaseService releaseService,
			ArtistReleaseService artistReleaseService,
			LabelService labelService,
			StyleService styleService,
			GenreService genreService,
			ReleaseLabelService releaseLabelService,
			ReleaseGenreService releaseGenreService,
			ReleaseStyleService releaseStyleService) {
		this.discogFeign = discogFeign;
		this.artistService = artistService;
		this.releaseService = releaseService;
		this.artistReleaseService = artistReleaseService;
		this.labelService = labelService;
		this.styleService = styleService;
		this.genreService = genreService;
		this.releaseLabelService = releaseLabelService;
		this.releaseGenreService = releaseGenreService;
		this.releaseStyleService = releaseStyleService;
		
		
	}

	@Override
	public ResponseEntity<ResponseWrapper<ArtistDiscogResponseDto>> getArtist(
			ArtistRequestDto artistRequestDto) {
		ResponseEntity<ArtistDiscogResponseDto> discogResp =discogFeign.getArtist(artistRequestDto.getArtist(), artistRequestDto.getTitle(), artistRequestDto.getReleaseTitle(), artistRequestDto.getToken());
		
		//Get the mastersId field
		Integer masterId = discogResp.getBody().getResults().get(0).getMasterId();
		ResponseEntity<MasterResponseDto> masterResponse = discogFeign.getMaster(masterId);
		
		//Extract and get the artist
		ResponseEntity<ArtistResponseDto> artistResponseDto = discogFeign.getArtistById(masterResponse.getBody().getArtists().get(0).getId());
		
//		ResponseEntity<ResponseWrapper<ArtistResponseDto>> artistResp = this.artistService.insert(artistResponseDto.getBody());
		
		//Save albums/releases
		WebClient webClient = Webclient.getClient(artistResponseDto.getBody().getReleasesUrl());
		ArtistResponseDto artistResponseDto2 = webClient.get().exchange().block().bodyToMono(ArtistResponseDto.class).block();
		artistResponseDto2.setReleasesUrl(artistResponseDto.getBody().getReleasesUrl());
		ResponseEntity<ResponseWrapper<List<ReleaseDto>>> releases = ReleaseWebClient.getArtistReleases(artistResponseDto.getBody());
		
		//Query artist by name in H2 database
		artistResponseDto2.setName(artistRequestDto.getArtist());
		ResponseEntity<ResponseWrapper<ArtistResponseDto>> artistQueryResp = this.artistService.findByName(artistResponseDto2);
		Integer newArtistId = null;
		if(!artistQueryResp.getStatusCode().is2xxSuccessful()) {
			newArtistId = this.artistService.insert(artistResponseDto.getBody()).getBody().getData().getId().intValue();
		}else {
			newArtistId = artistQueryResp.getBody().getData().getId().intValue();
		}
		
		ResponseEntity<ResponseWrapper<List<ReleaseDto>>> savedReleases = new ResponseEntity<>(
				ResponseWrapper.<List<ReleaseDto>>builder()
				.data(new ArrayList<>())
				.message("No results found")
				.status(HttpStatus.OK)
				.build(),
				HttpStatus.NOT_FOUND);
		
		//Save discography
		List<ReleaseDto> lstReleaseDtos = new ArrayList<>();
		final Integer artistId = newArtistId;
		discogResp.getBody().getResults().forEach(res-> {
			ReleaseDto releaseDto = ReleaseMapper.INSTANCE.toDto(res);
			releaseDto.setArtistId(artistId);
			lstReleaseDtos.add(releaseDto);
			Integer releaseId = this.releaseService.save(releaseDto).getBody().getData().getId();
			res.setId(releaseId);
			this.setGenres(res);
			this.setLabels(res);
			this.setStyles(res);
		});
		//this.releaseService.save(lstReleaseDtos);
		
		
		return new ResponseEntity<>(
				ResponseWrapper.<ArtistDiscogResponseDto>builder()
				.data(discogResp.getBody())
				.message(discogResp.getStatusCode().is2xxSuccessful() ? "OK" : "No results found")
				.status(HttpStatus.valueOf(discogResp.getStatusCodeValue()))
				.build(),
				discogResp.getStatusCode());
	}

	@Override
	public ResponseEntity<ResponseWrapper<ArtistComparissonResponseDto>> compareArtists(
			ArtistComparissonRequestDto artistComparissonRequestDto) {
		List<ArtistResponseDto> lstResp = new ArrayList<>();
		for(String artist : artistComparissonRequestDto.getArtists()) {
			ResponseEntity<ResponseWrapper<ArtistResponseDto>> artistResp = this.artistService.findByName(ArtistResponseDto.builder().name(artist).build());
			if(!artistResp.getStatusCode().is2xxSuccessful()) {
				//Get artist data from Discogs API and Save It into the database
				this.getArtist(ArtistRequestDto.builder().artist(artist).token(discogToken).build());
				
				//Gets the saved data from local database
				ResponseEntity<ResponseWrapper<ArtistResponseDto>> artistData = this.artistService.findByName(ArtistResponseDto.builder().name(artist).build());
				lstResp.add(artistData.getBody().getData());
			}else {
				lstResp.add(artistResp.getBody().getData());
			}
		}
		ArtistComparissonResponseDto artistComparissonResponseDto = ArtistComparissonResponseDto.builder().artists(lstResp).build();
		return new ResponseEntity<>(
				ResponseWrapper.<ArtistComparissonResponseDto>builder()
				.data(artistComparissonResponseDto)
				.message(artistComparissonResponseDto.getArtists().size()>0 ? "OK" : "No results found")
				.status(HttpStatus.OK)
				.build(),
				HttpStatus.OK);
	}
	
	public List<Genre> setGenres(ResultDto resultDto){
		for(String genre: resultDto.getGenre()) {
			GenreDto genreDto = GenreDto.builder().genreName(genre).build();
			ResponseEntity<ResponseWrapper<GenreDto>> genreResp = this.genreService.findByName(genreDto);
			if(!genreResp.getStatusCode().is2xxSuccessful()) {
				ResponseEntity<ResponseWrapper<GenreDto>> genreSaveResp =this.genreService.insert(genreDto);
				genreDto = genreSaveResp.getBody().getData();
			}
			ReleaseDto releaseDto = ReleaseMapper.INSTANCE.toDto(resultDto);
			//releaseDto.setg
			this.saveReleaseGenre(ReleaseMapper.INSTANCE.toDto(resultDto), genreDto);
		}
		return null;
	}
	
	public Object saveReleaseGenre(ReleaseDto releaseDto, GenreDto genreDto) {
		ResponseEntity<ResponseWrapper<ReleaseGenreDto>> releaseGenreResp = this.releaseGenreService.findByReleaseAndGenre(releaseDto.getId(), genreDto.getId());
		if(!releaseGenreResp.getStatusCode().is2xxSuccessful()) {
			this.releaseGenreService.insert(ReleaseGenreDto.builder().genre(genreDto).release(releaseDto).build());
		}
		return null;
	}
	
	public List<Label> setLabels(ResultDto resultDto){
		for(String label: resultDto.getLabel()) {
			LabelDto labelDto = LabelDto.builder().labelName(label).build();
			ResponseEntity<ResponseWrapper<LabelDto>> labelResp = this.labelService.findByName(labelDto);
			if(!labelResp.getStatusCode().is2xxSuccessful()) {
				ResponseEntity<ResponseWrapper<LabelDto>> labelSaveResp = this.labelService.insert(labelDto);
				labelDto = labelSaveResp.getBody().getData();
			}else {
				labelDto = labelResp.getBody().getData();
			}
			
			this.saveReleaseLabel(ReleaseMapper.INSTANCE.toDto(resultDto), labelDto);
			
		}
		return null;
	}
	
	public Object saveReleaseLabel(ReleaseDto releaseDto, LabelDto labelDto) {
		ResponseEntity<ResponseWrapper<ReleaseLabelDto>> releaseLabelResp = this.releaseLabelService.findByReleaseAndLabel(releaseDto.getId(), labelDto.getId());
		if(!releaseLabelResp.getStatusCode().is2xxSuccessful()) {
			this.releaseLabelService.insert(ReleaseLabelDto.builder().release(releaseDto).label(labelDto).build());
		}
		return null;
	}
	
	public List<Style> setStyles(ResultDto resultDto){
		for(String style: resultDto.getStyle()) {
			StyleDto styleDto = StyleDto.builder().styleName(style).build();
			ResponseEntity<ResponseWrapper<GenreDto>> genreResp = this.styleService.findByName(styleDto);
			if(genreResp.getStatusCode().is2xxSuccessful()) {
				this.styleService.insert(styleDto);
			}
			
			this.saveReleaseStyle(ReleaseMapper.INSTANCE.toDto(resultDto), styleDto);
			
		}
		return null;
	}
	
	public Object saveReleaseStyle(ReleaseDto releaseDto, StyleDto styleDto) {
		ResponseEntity<ResponseWrapper<ReleaseStyleDto>> releaseGenreResp = this.releaseStyleService.findByReleaseAndStyle(releaseDto.getId(), styleDto.getId());
		if(!releaseGenreResp.getStatusCode().is2xxSuccessful()) {
			this.releaseStyleService.insert(ReleaseStyleDto.builder().release(releaseDto).style(styleDto).build());
		}
		return null;
	}

	
}
