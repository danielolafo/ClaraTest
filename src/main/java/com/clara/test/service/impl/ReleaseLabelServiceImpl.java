package com.clara.test.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.clara.test.dto.ReleaseLabelDto;
import com.clara.test.dto.ResponseWrapper;
import com.clara.test.repository.ReleaseLabelRepository;
import com.clara.test.service.ReleaseLabelService;

import lombok.NonNull;
@Service
public class ReleaseLabelServiceImpl implements ReleaseLabelService{
	
	@NonNull
	private ReleaseLabelRepository repository;
	
	public ReleaseLabelServiceImpl(ReleaseLabelRepository repository) {
		this.repository = repository;
	}

	@Override
	public ResponseEntity<ResponseWrapper<ReleaseLabelDto>> insert(ReleaseLabelDto releaseLabelDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<ResponseWrapper<ReleaseLabelDto>> findByReleaseAndLabel(Integer releaseId, Integer labelId) {
		// TODO Auto-generated method stub
		return null;
	}

}
