package com.clara.test.service.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.clara.test.dto.ReleaseDto;
import com.clara.test.dto.ResponseWrapper;
import com.clara.test.entity.Release;
import com.clara.test.mapper.ReleaseMapper;
import com.clara.test.repository.ReleaseRepository;
import com.clara.test.service.ReleaseService;

import lombok.NonNull;

@Service
public class ReleaseServiceImpl implements ReleaseService {
	
	@NonNull
	private ReleaseRepository repository;
	
	public ReleaseServiceImpl(ReleaseRepository repository) {
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
		// TODO Auto-generated method stub
		return null;
	}

}
