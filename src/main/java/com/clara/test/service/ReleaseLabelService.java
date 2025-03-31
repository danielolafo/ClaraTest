package com.clara.test.service;

import org.springframework.http.ResponseEntity;

import com.clara.test.dto.ReleaseLabelDto;
import com.clara.test.dto.ResponseWrapper;

public interface ReleaseLabelService {
	public ResponseEntity<ResponseWrapper<ReleaseLabelDto>> insert(
			ReleaseLabelDto releaseLabelDto);
	
	public ResponseEntity<ResponseWrapper<ReleaseLabelDto>> findByReleaseAndLabel(
			Integer releaseId, Integer labelId);
}
