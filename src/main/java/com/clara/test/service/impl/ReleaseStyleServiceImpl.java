package com.clara.test.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.clara.test.dto.ReleaseStyleDto;
import com.clara.test.dto.ResponseWrapper;
import com.clara.test.repository.ReleaseStyleRepository;
import com.clara.test.service.ReleaseStyleService;

import lombok.NonNull;
@Service
public class ReleaseStyleServiceImpl implements ReleaseStyleService {
	
	@NonNull
	private ReleaseStyleRepository repository;
	
	public ReleaseStyleServiceImpl(ReleaseStyleRepository repository) {
		this.repository = repository;
	}

	@Override
	public ResponseEntity<ResponseWrapper<ReleaseStyleDto>> insert(ReleaseStyleDto releaseStyleDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<ResponseWrapper<ReleaseStyleDto>> findByReleaseAndStyle(Integer releaseId, Integer styleId) {
		// TODO Auto-generated method stub
		return null;
	}

}
