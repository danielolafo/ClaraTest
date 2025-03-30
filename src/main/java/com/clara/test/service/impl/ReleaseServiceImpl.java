package com.clara.test.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.clara.test.dto.ArtistReleaseDto;
import com.clara.test.dto.ArtistResponseDto;
import com.clara.test.dto.CommunityDto;
import com.clara.test.dto.ReleaseDto;
import com.clara.test.dto.ResponseWrapper;
import com.clara.test.entity.Community;
import com.clara.test.entity.Release;
import com.clara.test.mapper.CommunityMapper;
import com.clara.test.mapper.ReleaseMapper;
import com.clara.test.repository.ReleaseRepository;
import com.clara.test.service.ArtistReleaseService;
import com.clara.test.service.ArtistService;
import com.clara.test.service.CommunityService;
import com.clara.test.service.ReleaseService;

import lombok.NonNull;

@Service
public class ReleaseServiceImpl implements ReleaseService {
	
	@NonNull
	private ArtistService artistService;
	
	@NonNull
	private CommunityService communityService;
	
	@NonNull
	private ArtistReleaseService artistReleaseService;
	
	@NonNull
	private ReleaseRepository repository;
	
	public ReleaseServiceImpl(
			ArtistService artistService,
			CommunityService communityService,
			ArtistReleaseService artistReleaseService,
			ReleaseRepository repository) {
		this.artistService = artistService;
		this.communityService = communityService;
		this.artistReleaseService = artistReleaseService;
		this.repository = repository;
	}

	@Override
	public ResponseEntity<ResponseWrapper<ReleaseDto>> insert(ReleaseDto releaseDto) {
		Release release = ReleaseMapper.INSTANCE.toEntity(releaseDto);
		release.setId(null);
		this.repository.save(release);
		releaseDto.setId(release.getId());
		return new ResponseEntity<>(
				ResponseWrapper.<ReleaseDto>builder()
				.data(releaseDto)
				.build(),
				HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseWrapper<List<ReleaseDto>>> insert(List<ReleaseDto> lstReleaseDtos) {
		for(ReleaseDto releaseDto : lstReleaseDtos) {
			releaseDto.setId(null);
			ResponseEntity<ResponseWrapper<ReleaseDto>> resp = this.insert(releaseDto);
			releaseDto.setId(resp.getBody().getData().getId());
		}
		return new ResponseEntity<>(
				ResponseWrapper.<List<ReleaseDto>>builder()
				.data(lstReleaseDtos)
				.build(),
				HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseWrapper<List<ReleaseDto>>> getReleasesByArtist(Integer artistId) {
		//ResponseEntity<ResponseWrapper<ArtistResponseDto>> artistResp = this.artistService.findById(artistId);
		List<ReleaseDto> lstReleaseDtos = new ArrayList<>();
		this.repository.findByArtist(artistId).forEach(rel -> lstReleaseDtos.add(ReleaseMapper.INSTANCE.toDto(rel)));
		return new ResponseEntity<>(
				ResponseWrapper.<List<ReleaseDto>>builder()
				.data(lstReleaseDtos)
				.build(),
				!lstReleaseDtos.isEmpty()?  HttpStatus.OK : HttpStatus.NOT_FOUND);
	}

	@Override
	public ResponseEntity<ResponseWrapper<List<ReleaseDto>>> save(List<ReleaseDto> lstReleaseDtos) {
		//Get the artist info 
		ResponseEntity<ResponseWrapper<ArtistResponseDto>> artistResp = this.artistService.findById(lstReleaseDtos.get(0).getArtistId());
		List<Release> lstCurrentReleases = this.repository.findByArtist(artistResp.getBody().getData().getId().intValue());
		
		//Search for unsaved releases		
		for(ReleaseDto releaseDto : lstReleaseDtos) {
			List<Release> lstReleases = lstCurrentReleases.stream().filter(rel -> rel.getTitle().equals(lstCurrentReleases)).collect(Collectors.toList());
			if(lstReleases.isEmpty()) {
				//Is new. Save It into the database
				releaseDto.setId(null);
				Community community = releaseDto.getCommunity();
				ResponseEntity<ResponseWrapper<List<CommunityDto>>> lstSavedCommunities = this.communityService.save(List.of(CommunityMapper.INSTANCE.toDto(community)));
				Release release = ReleaseMapper.INSTANCE.toEntity(releaseDto);
				release.setCommunity(CommunityMapper.INSTANCE.toEntity(lstSavedCommunities.getBody().getData().get(0)));
				release = this.repository.save(release);
				releaseDto.setId(release.getId());
				
				//Look for an artist-release relationship saved
				ResponseEntity<ResponseWrapper<List<ArtistReleaseDto>>> artistRelease = this.artistReleaseService.findByNameAndTitle(
						ArtistReleaseDto.builder().artist(artistResp.getBody().getData().getName()).title(release.getTitle()).build());
				if(!artistRelease.getStatusCode().is2xxSuccessful()) {
					this.artistReleaseService.insert(ArtistReleaseDto.builder().artistResponseDto(artistResp.getBody().getData()).release(releaseDto).build());
				}
			}
		}
		
		//Save into ArtistRelease
		return new ResponseEntity<>(
				ResponseWrapper.<List<ReleaseDto>>builder()
				.data(lstReleaseDtos)
				.build(),
				!lstReleaseDtos.isEmpty()?  HttpStatus.OK : HttpStatus.NOT_FOUND);
	}

}
