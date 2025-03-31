package com.clara.test.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.clara.test.dto.LabelDto;
import com.clara.test.dto.ResponseWrapper;

public interface LabelService {
	public ResponseEntity<ResponseWrapper<LabelDto>> insert(
			LabelDto labelDto);
	
	public ResponseEntity<ResponseWrapper<LabelDto>> findByName(
			LabelDto labelDto);
	
	public ResponseEntity<ResponseWrapper<List<LabelDto>>> getLabelFrequencyByArtis(Integer artistId);
}
