package com.clara.test.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.clara.test.dto.ArtistDiscogResponseDto;
import com.clara.test.dto.ArtistReleaseDto;
import com.clara.test.dto.ResponseWrapper;
import com.clara.test.entity.ArtistRelease;
import com.clara.test.mapper.ArtistMapper;
import com.clara.test.mapper.ArtistReleaseMapper;
import com.clara.test.repository.ArtistReleaseRepository;
import com.clara.test.service.ArtistReleaseService;

import lombok.NonNull;

@Service
public class ArtistReleaseServiceImpl implements ArtistReleaseService {
	
	@NonNull
	private ArtistReleaseRepository repository;
	
	public ArtistReleaseServiceImpl(ArtistReleaseRepository repository) {
		this.repository = repository;
	}

	@Override
	public ResponseEntity<ResponseWrapper<List<ArtistReleaseDto>>> saveAll(
			List<ArtistReleaseDto> lstArtistReleaseDtos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<ResponseWrapper<ArtistReleaseDto>> save(ArtistReleaseDto artistReleaseDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<ResponseWrapper<List<ArtistReleaseDto>>> findByNameAndTitle(
			ArtistReleaseDto artistReleaseDto) {
		List<ArtistReleaseDto> lstResp = new ArrayList<>();
		Optional<ArtistRelease> artistReleaseOpt = this.repository.findByArtistNameAndReleaseTitle(artistReleaseDto.getArtist(), artistReleaseDto.getTitle());
		if(artistReleaseOpt.isPresent()) {
			lstResp.add(ArtistReleaseMapper.INSTANCE.toDto(artistReleaseOpt.get()));
		}
		
		return new ResponseEntity<>(
				ResponseWrapper.<List<ArtistReleaseDto>>builder()
				.data(lstResp)
				.message(!lstResp.isEmpty() ? "OK" : "No results found")
				.status(!lstResp.isEmpty() ? HttpStatus.OK : HttpStatus.NOT_FOUND)
				.build(),
				!lstResp.isEmpty() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
	}

	@Override
	public ResponseEntity<ResponseWrapper<ArtistReleaseDto>> insert(ArtistReleaseDto artistReleaseDto) {
		ArtistRelease artistRelease = ArtistReleaseMapper.INSTANCE.toEntity(artistReleaseDto);
		artistRelease.setArtist(ArtistMapper.INSTANCE.toArtist(artistReleaseDto.getArtistResponseDto()));
		artistRelease = this.repository.save(artistRelease);
		artistReleaseDto.setId(artistRelease.getId());
		return new ResponseEntity<>(
				ResponseWrapper.<ArtistReleaseDto>builder()
				.data(artistReleaseDto)
				.message(Objects.nonNull(artistReleaseDto.getId()) ? "OK" : "No results found")
				.status(Objects.nonNull(artistReleaseDto.getId()) ? HttpStatus.OK : HttpStatus.NOT_FOUND)
				.build(),
				Objects.nonNull(artistReleaseDto.getId()) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
	}

}
