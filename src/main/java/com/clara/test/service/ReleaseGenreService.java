package com.clara.test.service;

import org.springframework.http.ResponseEntity;

import com.clara.test.dto.ReleaseGenreDto;
import com.clara.test.dto.ResponseWrapper;

public interface ReleaseGenreService {

	
	public ResponseEntity<ResponseWrapper<ReleaseGenreDto>> insert(
			ReleaseGenreDto releaseGenreDto);
	
	public ResponseEntity<ResponseWrapper<ReleaseGenreDto>> findByReleaseAndGenre(
			Integer releaseId, Integer genreIds);
}
