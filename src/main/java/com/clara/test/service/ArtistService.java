package com.clara.test.service;

import org.springframework.http.ResponseEntity;

import com.clara.test.dto.ArtistResponseDto;
import com.clara.test.dto.ResponseWrapper;

public interface ArtistService {
	
	/**
	 * 
	 * @param artistResponseDto
	 * @return
	 * @author Daniel Orlando López Ochoa
	 */
	public ResponseEntity<ResponseWrapper<ArtistResponseDto>> getArtist(ArtistResponseDto artistResponseDto);
	
	/**
	 * 
	 * @param artistResponseDto
	 * @return
	 * @author Daniel Orlando López Ochoa
	 */
	public ResponseEntity<ResponseWrapper<ArtistResponseDto>> insert(ArtistResponseDto artistResponseDto);
	
	/**
	 * 
	 * @param artistResponseDto
	 * @return
	 * @author Daniel Orlando López Ochoa
	 */
	public ResponseEntity<ResponseWrapper<ArtistResponseDto>> findByName(ArtistResponseDto artistResponseDto);

}
