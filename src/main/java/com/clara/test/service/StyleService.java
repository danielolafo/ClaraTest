package com.clara.test.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.clara.test.dto.ResponseWrapper;
import com.clara.test.dto.StyleDto;

public interface StyleService {
	
	/**
	 * <p>Create a new Style</p>
	 * @param styleDto
	 * @return
	 * @author Daniel Orlando López Ochoa
	 */
	public ResponseEntity<ResponseWrapper<StyleDto>> insert(
			StyleDto styleDto);
	
	
	/**
	 * <p>Get a Style find exact name coincidence. If no result returns an answer with 404 code status</p>
	 * @param styleDto
	 * @return
	 * @author Daniel Orlando López Ochoa
	 */
	public ResponseEntity<ResponseWrapper<StyleDto>> findByName(
			StyleDto styleDto);
	
	/**
	 * <p>Get the list of styles for all releases of an artist and Its frequency (the number of appearances in all artist releases)</p>
	 * @param artistId
	 * @return
	 * @author Daniel Orlando López Ochoa
	 */
	public ResponseEntity<ResponseWrapper<List<StyleDto>>> getStyleFrequencyByArtist(Integer artistId);
}
