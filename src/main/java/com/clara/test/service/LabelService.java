package com.clara.test.service;

import org.springframework.http.ResponseEntity;

import com.clara.test.dto.GenreDto;
import com.clara.test.dto.LabelDto;
import com.clara.test.dto.ResponseWrapper;

public interface LabelService {
	public ResponseEntity<ResponseWrapper<GenreDto>> insert(
			LabelDto labelDto);
	
	public ResponseEntity<ResponseWrapper<GenreDto>> findByName(
			LabelDto labelDto);
}
