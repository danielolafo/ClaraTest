package com.clara.test.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.clara.test.dto.GenreDto;
import com.clara.test.dto.LabelDto;
import com.clara.test.dto.ResponseWrapper;
import com.clara.test.repository.LabelRepository;
import com.clara.test.service.LabelService;

import lombok.NonNull;
@Service
public class LabelServiceImpl implements LabelService {
	
	@NonNull
	private LabelRepository repository;
	
	public LabelServiceImpl(LabelRepository repository) {
		this.repository = repository;
	}

	@Override
	public ResponseEntity<ResponseWrapper<GenreDto>> insert(LabelDto labelDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<ResponseWrapper<GenreDto>> findByName(LabelDto labelDto) {
		// TODO Auto-generated method stub
		return null;
	}

}
