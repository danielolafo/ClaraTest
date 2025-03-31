package com.clara.test.service;

import org.springframework.http.ResponseEntity;

import com.clara.test.dto.GenreDto;
import com.clara.test.dto.ResponseWrapper;

public interface GenreService {

	public ResponseEntity<ResponseWrapper<GenreDto>> insert(
			GenreDto genreDto);
	
	public ResponseEntity<ResponseWrapper<GenreDto>> findByName(
			GenreDto genreDto);
}
