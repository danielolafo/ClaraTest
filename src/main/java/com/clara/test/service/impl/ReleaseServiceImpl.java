package com.clara.test.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.clara.test.dto.ArtistResponseDto;
import com.clara.test.dto.ReleaseDto;
import com.clara.test.dto.ResponseWrapper;
import com.clara.test.entity.Release;
import com.clara.test.mapper.ReleaseMapper;
import com.clara.test.repository.ReleaseRepository;
import com.clara.test.service.ArtistService;
import com.clara.test.service.ReleaseService;

import lombok.NonNull;

@Service
public class ReleaseServiceImpl implements ReleaseService {
	
	@NonNull
	private ArtistService artistService;
	
	@NonNull
	private ReleaseRepository repository;
	
	public ReleaseServiceImpl(
			ArtistService artistService,
			ReleaseRepository repository) {
		this.artistService = artistService;
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
				Release release = this.repository.save(ReleaseMapper.INSTANCE.toEntity(releaseDto));
				releaseDto.setId(release.getId());
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
