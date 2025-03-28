package com.clara.test.service;

import org.springframework.http.ResponseEntity;

import com.clara.test.dto.ArtistDiscogResponseDto;
import com.clara.test.dto.ArtistRequestDto;
import com.clara.test.dto.ResponseWrapper;

public interface DiscogService {

	public ResponseEntity<ResponseWrapper<ArtistDiscogResponseDto>> getArtist(ArtistRequestDto artistRequestDto);
	
}
