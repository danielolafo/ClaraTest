package com.clara.test.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.hibernate.exception.JDBCConnectionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.clara.test.client.ReleaseWebClient;
import com.clara.test.dto.ArtistComparissonRequestDto;
import com.clara.test.dto.ArtistComparissonResponseDto;
import com.clara.test.dto.ArtistDiscogResponseDto;
import com.clara.test.dto.ArtistDto;
import com.clara.test.dto.ArtistRequestDto;
import com.clara.test.dto.ArtistResponseDto;
import com.clara.test.dto.DiscographyRequestDto;
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
import com.clara.test.dto.ValidationDto;
import com.clara.test.entity.Genre;
import com.clara.test.entity.Label;
import com.clara.test.entity.Style;
import com.clara.test.exception.DatabaseException;
import com.clara.test.exception.InvalidValueException;
import com.clara.test.exception.NotFoundException;
import com.clara.test.feign.DiscogFeign;
import com.clara.test.mapper.ArtistMapper;
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
import com.clara.test.utils.CriteriaEnum;
import com.clara.test.utils.Webclient;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
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
			ArtistRequestDto artistRequestDto) throws InvalidValueException, DatabaseException {
		log.info("{} artistRequestDto: {}", Thread.currentThread().getStackTrace()[1].getMethodName(), artistRequestDto);
		try {
			ValidationDto validationDto = this.validate(artistRequestDto);
			if(!validationDto.isValid()) {
				return new ResponseEntity<>(
						ResponseWrapper.<ArtistDiscogResponseDto>builder()
						.data(new ArtistDiscogResponseDto())
						.message(validationDto.getMessage())
						.status(HttpStatus.BAD_REQUEST)
						.build(),
						HttpStatus.BAD_REQUEST);
			}
			
			ResponseEntity<ArtistDiscogResponseDto> discogResp =discogFeign.getArtist(artistRequestDto.getArtist(), artistRequestDto.getTitle(), artistRequestDto.getReleaseTitle(), discogToken);
			
			//Get the mastersId field
			Integer masterId = discogResp.getBody().getResults().get(0).getMasterId();
			ResponseEntity<MasterResponseDto> masterResponse = discogFeign.getMaster(masterId);
			
			//Extract and get the artist
			ResponseEntity<ArtistResponseDto> artistResponseDto = discogFeign.getArtistById(masterResponse.getBody().getArtists().get(0).getId());
			
			
			
			
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
			
			
			return new ResponseEntity<>(
					ResponseWrapper.<ArtistDiscogResponseDto>builder()
					.data(discogResp.getBody())
					.message(discogResp.getStatusCode().is2xxSuccessful() ? "OK" : "No results found")
					.status(HttpStatus.valueOf(discogResp.getStatusCodeValue()))
					.build(),
					discogResp.getStatusCode());
		}catch(JDBCConnectionException ex) {
			log.error("{} Error: {}", Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
			throw new DatabaseException("Could not connect to the database");
		}
		catch(Exception ex) {
			log.error("{} Error: {}", Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
			throw new InvalidValueException("The artist could not be retrieved");
		}
	}

	@Override
	public ResponseEntity<ResponseWrapper<ArtistComparissonResponseDto>> compareArtists(
			ArtistComparissonRequestDto artistComparissonRequestDto) {
		log.info("{} artistComparissonRequestDto: {}", Thread.currentThread().getStackTrace()[1].getMethodName(),artistComparissonRequestDto);
		try {
			ValidationDto validationDto = this.validate(artistComparissonRequestDto);
			if(!validationDto.isValid()) {
				return new ResponseEntity<>(
						ResponseWrapper.<ArtistComparissonResponseDto>builder()
						.data(ArtistComparissonResponseDto.builder().build())
						.message(validationDto.getMessage())
						.status(HttpStatus.BAD_REQUEST)
						.build(),
						HttpStatus.BAD_REQUEST);
			}
			
			List<ArtistDto> lstResp = new ArrayList<>();
			for(String artist : artistComparissonRequestDto.getArtists()) {
				ResponseEntity<ResponseWrapper<ArtistResponseDto>> artistResp = this.artistService.findByName(ArtistResponseDto.builder().name(artist).build());
				ArtistDto artistDto = null;
				if(!artistResp.getStatusCode().is2xxSuccessful()) {
					//The artist does not exist in the database
					
					//Get artist data from Discogs API and Save It into the database
					ResponseEntity<ResponseWrapper<ArtistDiscogResponseDto>> respSaveArtist = this.getArtist(ArtistRequestDto.builder().artist(artist).token(discogToken).build());
					
					//Gets the saved data from local database
					ResponseEntity<ResponseWrapper<ArtistResponseDto>> artistData = this.artistService.findByName(ArtistResponseDto.builder().name(artist).build());
					
					//Get Genres
//					ResponseEntity<ResponseWrapper<List<GenreDto>>> lstRespGenresDtos = this.genreService.findByArtist(artistData.getBody().getData().getId().intValue());
					ResponseEntity<ResponseWrapper<List<GenreDto>>> lstRespGenresDtos = this.genreService.getGenreFrequencyByArtis(artistData.getBody().getData().getId().intValue());
					artistDto = ArtistMapper.INSTANCE.toDto(artistData.getBody().getData());
					artistDto.setLstGenres(lstRespGenresDtos.getBody().getData().stream().map(G -> G.getGenreName()).toList());
					artistDto.setNumberOfReleases(respSaveArtist.getBody().getData().getPagination().getItems());
					//lstResp.add(artistDto);
					
				}else {
					//The artist exists in the database
					
					artistDto = ArtistMapper.INSTANCE.toDto(artistResp.getBody().getData());
					//Gets the saved data from local database
					ResponseEntity<ResponseWrapper<ArtistResponseDto>> artistData = this.artistService.findByName(ArtistResponseDto.builder().name(artist).build());
					
					//Get Genres
					ResponseEntity<ResponseWrapper<List<GenreDto>>> lstRespGenresDtos = this.genreService.getGenreFrequencyByArtis(artistData.getBody().getData().getId().intValue());
					
					artistDto.setLstGenres(lstRespGenresDtos.getBody().getData().stream().map(G -> G.getGenreName()).toList());
					//lstResp.add(artistDto);
				}
				ResponseEntity<ResponseWrapper<List<LabelDto>>> labelsResp = this.labelService.getLabelFrequencyByArtis(artistDto.getId().intValue());
				artistDto.setLstTags(labelsResp.getBody().getData().stream().map(lab -> lab.getLabelName()).collect(Collectors.toList()));
				ResponseEntity<ResponseWrapper<List<StyleDto>>> styleResp =  this.styleService.getStyleFrequencyByArtist(artistDto.getId().intValue());
				artistDto.setLstStyles(styleResp.getBody().getData().stream().map(style -> style.getStyleName()).collect(Collectors.toList()));
				lstResp.add(artistDto);
				
				//Set criteria data
				this.getCriteriaData(artistDto, artistComparissonRequestDto.getCriteria());
			}
			
			ArtistComparissonResponseDto artistComparissonResponseDto = ArtistComparissonResponseDto.builder().artists(lstResp).build();
			this.setLightweightDto(artistComparissonResponseDto);
			return new ResponseEntity<>(
					ResponseWrapper.<ArtistComparissonResponseDto>builder()
					.data(artistComparissonResponseDto)
					.message(artistComparissonResponseDto.getArtists().size()>0 ? "OK" : "No results found")
					.status(HttpStatus.OK)
					.build(),
					HttpStatus.OK);
		}catch(Exception ex) {
			log.error("{} Error: {}", Thread.currentThread().getStackTrace()[1].getMethodName(), ex);
			return new ResponseEntity<>(
					ResponseWrapper.<ArtistComparissonResponseDto>builder()
					.data(ArtistComparissonResponseDto.builder().build())
					.message(HttpStatus.BAD_REQUEST.getReasonPhrase())
					.status(HttpStatus.BAD_REQUEST)
					.build(),
					HttpStatus.BAD_REQUEST);
		}
		
	}
	
	
	/**
	 * 
	 * @param resultDto
	 * @return
	 * @author Daniel Orlando López Ochoa
	 */
	public List<Genre> setGenres(ResultDto resultDto){
		log.info("{} resultDto: {}", Thread.currentThread().getStackTrace()[1].getMethodName(), resultDto);
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
	
	
	/**
	 * 
	 * @param releaseDto
	 * @param genreDto
	 * @return
	 * @author Daniel Orlando López Ochoa
	 */
	public Object saveReleaseGenre(ReleaseDto releaseDto, GenreDto genreDto) {
		log.info("{} releaseDto: {} genreDto: {}", Thread.currentThread().getStackTrace()[1].getMethodName(), releaseDto, genreDto);
		ResponseEntity<ResponseWrapper<ReleaseGenreDto>> releaseGenreResp = this.releaseGenreService.findByReleaseAndGenre(releaseDto.getId(), genreDto.getId());
		if(!releaseGenreResp.getStatusCode().is2xxSuccessful()) {
			this.releaseGenreService.insert(ReleaseGenreDto.builder().genre(genreDto).release(releaseDto).build());
		}
		return null;
	}
	
	
	/**
	 * 
	 * @param resultDto
	 * @return
	 * @author Daniel Orlando López Ochoa
	 */
	public List<Label> setLabels(ResultDto resultDto){
		log.info("{} resultDto:  {}", Thread.currentThread().getStackTrace()[1].getMethodName(), resultDto);
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
	
	/**
	 * 
	 * @param releaseDto
	 * @param labelDto
	 * @return
	 * @author Daniel Orlando López Ochoa
	 */
	public Object saveReleaseLabel(ReleaseDto releaseDto, LabelDto labelDto) {
		log.info("{} releaseDto: {} labelDto: {}", Thread.currentThread().getStackTrace()[1].getMethodName(),releaseDto, labelDto);
		ResponseEntity<ResponseWrapper<ReleaseLabelDto>> releaseLabelResp = this.releaseLabelService.findByReleaseAndLabel(releaseDto.getId(), labelDto.getId());
		if(!releaseLabelResp.getStatusCode().is2xxSuccessful()) {
			this.releaseLabelService.insert(ReleaseLabelDto.builder().release(releaseDto).label(labelDto).build());
		}
		return null;
	}
	
	/**
	 * 
	 * @param resultDto
	 * @return
	 * @author Daniel Orlando López Ochoa
	 */
	public List<Style> setStyles(ResultDto resultDto){
		log.info("{} resultDto: {}", Thread.currentThread().getStackTrace()[1].getMethodName(), resultDto);
		for(String style: resultDto.getStyle()) {
			StyleDto styleDto = StyleDto.builder().styleName(style).build();
			ResponseEntity<ResponseWrapper<StyleDto>> styleResp = this.styleService.findByName(styleDto);
			if(!styleResp.getStatusCode().is2xxSuccessful()) {
				ResponseEntity<ResponseWrapper<StyleDto>> styleSaveResp = this.styleService.insert(styleDto);
				styleDto = styleSaveResp.getBody().getData();
			}else {
				styleDto = styleResp.getBody().getData();
			}
			
			this.saveReleaseStyle(ReleaseMapper.INSTANCE.toDto(resultDto), styleDto);
			
		}
		return null;
	}
	
	/**
	 * 
	 * @param releaseDto
	 * @param styleDto
	 * @return
	 * @author Daniel Orlando López Ochoa
	 */
	public Object saveReleaseStyle(ReleaseDto releaseDto, StyleDto styleDto) {
		log.info("{} releaseDto: {}, styleDto : {}", Thread.currentThread().getStackTrace()[1].getMethodName(), releaseDto, styleDto);
		ResponseEntity<ResponseWrapper<ReleaseStyleDto>> releaseStyleResp = this.releaseStyleService.findByReleaseAndStyle(releaseDto.getId(), styleDto.getId());
		if(!releaseStyleResp.getStatusCode().is2xxSuccessful()) {
			this.releaseStyleService.insert(ReleaseStyleDto.builder().release(releaseDto).style(styleDto).build());
		}
		return null;
	}
	
	
	private void getCriteriaData(
			ArtistDto artistDto,
			String criteria) throws InvalidValueException, NotFoundException{
		log.info("getCriteriaData");
		CriteriaEnum criteriaEnum = CriteriaEnum.getEnum(criteria);
		switch(criteriaEnum) {
		case RELEASES:
			log.info("Releases");
			ResponseEntity<ResponseWrapper<List<ReleaseDto>>> releasesResp = this.releaseService.getReleasesByArtist(artistDto.getId().intValue());
			artistDto.setReleases(releasesResp.getBody().getData());
			break;
		case RELEASE_YEAR:
			log.info("ReleaseYear");
			this.setYearsActive(artistDto);
			break;
		case GENRES:
			log.info("Genres");
			ResponseEntity<ResponseWrapper<List<GenreDto>>> genresResp = this.genreService.findByArtist(artistDto.getId().intValue());
			artistDto.setLstGenres(genresResp.getBody().getData().stream().map(G -> G.getGenreName()).toList());
			break;
		case TAGS:
			log.info("Tags");
			//this.labelService.
			break;
		case STYLES:
			log.info("Styles");
			break;
		default:
			throw new InvalidValueException("Invalid criteria search for the comparisson"); 
		}
	}
	
	private void setYearsActive(ArtistDto artistDto) throws NotFoundException {
		log.info("{} ArtistDto : {}", Thread.currentThread().getStackTrace()[1].getMethodName(), artistDto);
		ResponseEntity<ResponseWrapper<List<Integer>>> lstReleaseYearsResp =  this.artistReleaseService.getReleaseYears(artistDto.getId().intValue());
		if(!lstReleaseYearsResp.getStatusCode().is2xxSuccessful()) {
			throw new NotFoundException("No releases found for this artist");
		}
		
		List<Integer> years = lstReleaseYearsResp.getBody().getData();
		artistDto.setYearsActive(years.get(years.size()-1)-years.get(0));
	}
	
	
	/**
	 * 
	 * @param artistComparissonResponseDto
	 * @author Daniel Orlando López Ochoa
	 */
	public void setLightweightDto(ArtistComparissonResponseDto artistComparissonResponseDto) {
		log.info("{} artistComparissonResponseDto : {}", Thread.currentThread().getStackTrace()[1].getMethodName(), artistComparissonResponseDto);
		List<ArtistDto> lstArtists = new ArrayList<>();
		for(ArtistDto artistDto : artistComparissonResponseDto.getArtists()) {
			ArtistDto lightweightArtistDto = ArtistDto.builder()
					.id(artistDto.getId())
					.lstGenres(artistDto.getLstGenres())
					.lstTags(artistDto.getLstTags())
					.name(artistDto.getName())
					.numberOfReleases(artistDto.getNumberOfReleases())
					.yearsActive(artistDto.getYearsActive())
					.build();
			lstArtists.add(lightweightArtistDto);
		}
		artistComparissonResponseDto.setArtists(lstArtists);
	}

	@Override
	public ResponseEntity<ResponseWrapper<ArtistDto>> getDiscography(DiscographyRequestDto discographyRequestDto) {
		log.info("{}  discographyRequestDto: {}", Thread.currentThread().getStackTrace()[1].getMethodName(), discographyRequestDto);
		ValidationDto validationDto = this.validate(discographyRequestDto);
		if(!validationDto.isValid()) {
			return new ResponseEntity<>(
					ResponseWrapper.<ArtistDto>builder()
					.data(ArtistDto.builder().build())
					.message(validationDto.getMessage())
					.status(HttpStatus.BAD_REQUEST)
					.build(),
					HttpStatus.BAD_REQUEST);
		}
		
		List<ReleaseDto> releases = new ArrayList<>();
		
		ResponseEntity<ResponseWrapper<ArtistResponseDto>> artistResp = this.artistService.findById(discographyRequestDto.getArtistId());
		if(!artistResp.getStatusCode().is2xxSuccessful()) {
			return new ResponseEntity<>(
					ResponseWrapper.<ArtistDto>builder()
					.data(ArtistDto.builder().build())
					.message("The artist does not exist")
					.status(HttpStatus.NOT_FOUND)
					.build(),
					HttpStatus.NOT_FOUND);
		}
		this.releaseService.getDiscography(discographyRequestDto).getBody().getData().forEach(rel -> releases.add(rel));;
		return new ResponseEntity<>(
				ResponseWrapper.<ArtistDto>builder()
				.data(ArtistDto.builder().name(artistResp.getBody().getData().getName()).releases(releases).build())
				.message(!releases.isEmpty() ? "OK" : "No results found")
				.status(HttpStatus.OK)
				.build(),
				HttpStatus.OK);
	}
	
	private ValidationDto validate(ArtistRequestDto artistRequestDto) {
		if(Objects.isNull(artistRequestDto) || Objects.isNull(artistRequestDto.getArtist())) {
			return ValidationDto.builder().isValid(false).message("The artist name is required").build();
		}
		return ValidationDto.builder().isValid(true).message("Validation OK").build();
	}
	
	private ValidationDto validate(ArtistComparissonRequestDto artistComparissonRequestDto) {
		if(Objects.isNull(artistComparissonRequestDto) 
				|| Objects.isNull(artistComparissonRequestDto.getArtists())
				|| artistComparissonRequestDto.getArtists().isEmpty() || artistComparissonRequestDto.getArtists().size()==1) {
			return ValidationDto.builder().isValid(false).message("There is not enough artists for make a comparisson").build();
		}
		return ValidationDto.builder().isValid(true).message("Validation OK").build();
	}
	
	private ValidationDto validate(DiscographyRequestDto discographyRequestDto) {
		if(Objects.isNull(discographyRequestDto) 
				|| Objects.isNull(discographyRequestDto.getPage()) || Objects.isNull(discographyRequestDto.getPageSize())) {
			return ValidationDto.builder().isValid(false).message("The pagination is required").build();
		}
		if(Objects.isNull(discographyRequestDto.getArtistId())) {
			return ValidationDto.builder().isValid(false).message("The artist id is requierd").build();
		}
		if(discographyRequestDto.getPage()<0 || discographyRequestDto.getPageSize().compareTo(1)==-1) {
			return ValidationDto.builder().isValid(false).message("The page cannot be negative. The page size cannot be 0 or less than 0").build();
		}
		return ValidationDto.builder().isValid(true).message("Validation OK").build();
	}

	
}
