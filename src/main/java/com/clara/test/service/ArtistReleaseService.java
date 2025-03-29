package com.clara.test.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.clara.test.dto.ArtistReleaseDto;
import com.clara.test.dto.ResponseWrapper;

public interface ArtistReleaseService {
	
	public ResponseEntity<ResponseWrapper<List<ArtistReleaseDto>>> saveAll(
			List<ArtistReleaseDto> lstArtistReleaseDtos);
	
	public ResponseEntity<ResponseWrapper<ArtistReleaseDto>> save(
			ArtistReleaseDto artistReleaseDto);
	
	public ResponseEntity<ResponseWrapper<List<ArtistReleaseDto>>> findByNameAndTitle(ArtistReleaseDto artistReleaseDto);
	
	public ResponseEntity<ResponseWrapper<ArtistReleaseDto>> insert(
			ArtistReleaseDto artistReleaseDto);

}
