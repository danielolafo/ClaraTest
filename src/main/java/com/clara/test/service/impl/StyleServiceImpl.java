package com.clara.test.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.clara.test.dto.ResponseWrapper;
import com.clara.test.dto.StyleDto;
import com.clara.test.repository.StyleRepository;
import com.clara.test.service.StyleService;

import lombok.NonNull;
@Service
public class StyleServiceImpl implements StyleService {
	
	@NonNull
	private StyleRepository repository;
	
	public StyleServiceImpl(StyleRepository repository) {
		this.repository = repository;
	}

	@Override
	public ResponseEntity<ResponseWrapper<StyleDto>> insert(StyleDto styleDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<ResponseWrapper<StyleDto>> findByName(StyleDto styleDto) {
		// TODO Auto-generated method stub
		return null;
	}

}
