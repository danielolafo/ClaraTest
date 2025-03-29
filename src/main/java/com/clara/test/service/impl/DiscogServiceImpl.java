package com.clara.test.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.clara.test.client.ReleaseWebClient;
import com.clara.test.dto.ArtistDiscogResponseDto;
import com.clara.test.dto.ArtistReleaseDto;
import com.clara.test.dto.ArtistRequestDto;
import com.clara.test.dto.ArtistResponseDto;
import com.clara.test.dto.MasterResponseDto;
import com.clara.test.dto.ReleaseDto;
import com.clara.test.dto.ResponseWrapper;
import com.clara.test.entity.ArtistRelease;
import com.clara.test.feign.DiscogFeign;
import com.clara.test.mapper.ArtistMapper;
import com.clara.test.mapper.ArtistReleaseMapper;
import com.clara.test.mapper.ReleaseMapper;
import com.clara.test.service.ArtistReleaseService;
import com.clara.test.service.ArtistService;
import com.clara.test.service.DiscogService;
import com.clara.test.service.ReleaseService;
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
	
	public DiscogServiceImpl(
			DiscogFeign discogFeign,
			ArtistService artistService,
			ReleaseService releaseService,
			ArtistReleaseService artistReleaseService) {
		this.discogFeign = discogFeign;
		this.artistService = artistService;
		this.releaseService = releaseService;
		this.artistReleaseService = artistReleaseService;
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
		
		ResponseEntity<ResponseWrapper<ArtistResponseDto>> artistResp = this.artistService.insert(artistResponseDto.getBody());
		
		//Save albums/releases
		WebClient webClient = Webclient.getClient(artistResp.getBody().getData().getReleasesUrl());
		ArtistResponseDto artistResponseDto2 = webClient.get().exchange().block().bodyToMono(ArtistResponseDto.class).block();
		artistResponseDto2.setReleasesUrl(artistResp.getBody().getData().getReleasesUrl());
		ResponseEntity<ResponseWrapper<List<ReleaseDto>>> releases = ReleaseWebClient.getArtistReleases(artistResponseDto2);
		
		//Query artist by name in H2 database
		artistResponseDto2.setName(artistRequestDto.getArtist());
		ResponseEntity<ResponseWrapper<ArtistResponseDto>> artistQueryResp = this.artistService.findByName(artistResponseDto2);
		
		//Query in ArtistRelease by Discogs Artist id and Release id
		//doing a JOIN between these three tables and filtering 
		//by Discogs values
//		ArtistReleaseDto artistReleaseDto = ArtistReleaseDto.builder().artist(artistResponseDto2.getName()).title("ANY").build();
//		ResponseEntity<ResponseWrapper<List<ArtistReleaseDto>>> artistReleaseResp = this.artistReleaseService.findByNameAndTitle(artistReleaseDto);
//		
//		if(artistReleaseResp.getStatusCode().is2xxSuccessful()) {
//			
//		}
		
		this.releaseService.insert(releases.getBody().getData());
		//Set current artist id
		releases.getBody().getData().stream().forEach(rel -> {
			Set<ArtistRelease> setArtistRelease = new HashSet<>();
			setArtistRelease.add(ArtistRelease.builder()
					.release(ReleaseMapper.INSTANCE.toEntity(rel))
					.artist(ArtistMapper.INSTANCE.toArtist(artistQueryResp.getBody().getData()))
					.build());
			rel.setReleaseArtistReleases(setArtistRelease);
			
			//Query in ArtistRelease by Discogs Artist id and Release id
			//doing a JOIN between these three tables and filtering 
			//by Discogs values
			ArtistReleaseDto artistReleaseDto = ArtistReleaseDto.builder().artist(artistResponseDto2.getName()).title(rel.getTitle()).build();
			ResponseEntity<ResponseWrapper<List<ArtistReleaseDto>>> artistReleaseResp = this.artistReleaseService.findByNameAndTitle(artistReleaseDto);
			ResponseEntity<ResponseWrapper<ArtistReleaseDto>> respArtRelease;
			
			if(artistReleaseResp.getStatusCode().is2xxSuccessful()) {
				setArtistRelease.add(ArtistRelease.builder().id(artistReleaseResp.getBody().getData().get(0).getId()).build());
				rel.setReleaseArtistReleases(setArtistRelease);
			}else{
				artistResponseDto2.setName(artistQueryResp.getBody().getData().getName());
				respArtRelease = this.artistReleaseService.insert(ArtistReleaseDto.builder().artistResponseDto(artistQueryResp.getBody().getData()).release(rel).artist(artistQueryResp.getBody().getData().getName()).build());
				setArtistRelease.add(ArtistReleaseMapper.INSTANCE.toEntity(respArtRelease.getBody().getData()));
				rel.setReleaseArtistReleases(setArtistRelease);
			}
		});
//		this.releaseService.insert(releases.getBody().getData());
		
		
		//Save tracks
		
		//Save artist members
		return new ResponseEntity<>(
				ResponseWrapper.<ArtistDiscogResponseDto>builder()
				.data(discogResp.getBody())
				.message(discogResp.getStatusCode().is2xxSuccessful() ? "OK" : "No results found")
				.status(HttpStatus.valueOf(discogResp.getStatusCodeValue()))
				.build(),
				discogResp.getStatusCode());
	}

	
}
