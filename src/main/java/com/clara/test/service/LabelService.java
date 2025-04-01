package com.clara.test.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.clara.test.dto.LabelDto;
import com.clara.test.dto.ResponseWrapper;

public interface LabelService {
	
	/**
	 * <p>Create a new label/tag category</p>
	 * @param labelDto
	 * @return
	 * @author Daniel Orlando López Ochoa
	 */
	public ResponseEntity<ResponseWrapper<LabelDto>> insert(
			LabelDto labelDto);
	
	/**
	 * <p>Find a label by exact name coincidence. If no results returns an answer with 404 status code</p>
	 * @param labelDto
	 * @return
	 * @author Daniel Orlando López Ochoa
	 */
	public ResponseEntity<ResponseWrapper<LabelDto>> findByName(
			LabelDto labelDto);
	
	
	/**
	 * <p></p>
	 * @param artistId
	 * @return
	 * @author Daniel Orlando López Ochoa
	 */
	public ResponseEntity<ResponseWrapper<List<LabelDto>>> getLabelFrequencyByArtis(Integer artistId);
}
