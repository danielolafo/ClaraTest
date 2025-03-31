package com.clara.test.service;

import org.springframework.http.ResponseEntity;

import com.clara.test.dto.GenreDto;
import com.clara.test.dto.ResponseWrapper;
import com.clara.test.dto.StyleDto;

public interface StyleService {
	public ResponseEntity<ResponseWrapper<GenreDto>> insert(
			StyleDto styleDto);
	
	public ResponseEntity<ResponseWrapper<GenreDto>> findByName(
			StyleDto styleDto);
}
