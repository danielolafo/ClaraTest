package com.clara.test.service;

import org.springframework.http.ResponseEntity;

import com.clara.test.dto.ArtistComparissonRequestDto;
import com.clara.test.dto.ArtistComparissonResponseDto;
import com.clara.test.dto.ArtistDiscogResponseDto;
import com.clara.test.dto.ArtistRequestDto;
import com.clara.test.dto.ResponseWrapper;
import com.clara.test.exception.InvalidValueException;

public interface DiscogService {

	public ResponseEntity<ResponseWrapper<ArtistDiscogResponseDto>> getArtist(ArtistRequestDto artistRequestDto) throws InvalidValueException;
	
	
	public ResponseEntity<ResponseWrapper<ArtistComparissonResponseDto>> compareArtists(ArtistComparissonRequestDto artistComparissonRequestDto);
	
}
