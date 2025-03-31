package com.clara.test.service;

import org.springframework.http.ResponseEntity;

import com.clara.test.dto.ReleaseStyleDto;
import com.clara.test.dto.ResponseWrapper;

public interface ReleaseStyleService {
	public ResponseEntity<ResponseWrapper<ReleaseStyleDto>> insert(
			ReleaseStyleDto releaseStyleDto);
	
	public ResponseEntity<ResponseWrapper<ReleaseStyleDto>> findByReleaseAndStyle(
			Integer releaseId, Integer styleId);
}
