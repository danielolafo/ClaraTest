package com.clara.test.service;

import org.springframework.http.ResponseEntity;

import com.clara.test.dto.ResponseWrapper;
import com.clara.test.dto.StyleDto;

public interface StyleService {
	public ResponseEntity<ResponseWrapper<StyleDto>> insert(
			StyleDto styleDto);
	
	public ResponseEntity<ResponseWrapper<StyleDto>> findByName(
			StyleDto styleDto);
}
