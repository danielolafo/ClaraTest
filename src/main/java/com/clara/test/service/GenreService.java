package com.clara.test.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.clara.test.dto.GenreDto;
import com.clara.test.dto.ResponseWrapper;

public interface GenreService {

	/**
	 * <p>Create a new Genre</p>
	 * @param genreDto
	 * @return
	 * @author Daniel Orlando López Ochoa
	 */
	public ResponseEntity<ResponseWrapper<GenreDto>> insert(
			GenreDto genreDto);
	
	/**
	 * <p>Find a Genre by exact name coincidence. If no reuslt returns an answer with 404 response</p>
	 * @param genreDto
	 * @return
	 * @author Daniel Orlando López Ochoa
	 */
	public ResponseEntity<ResponseWrapper<GenreDto>> findByName(
			GenreDto genreDto);
	
	/**
	 * <p>Find all genres asociated to an artist</p>
	 * @param artistId
	 * @return
	 * @author Daniel Orlando López Ochoa
	 */
	public ResponseEntity<ResponseWrapper<List<GenreDto>>> findByArtist(
			Integer artistId);
	
	
	/**
	 * <p>Find all genres and Its number of appearances in all the artist releases</p>
	 * @param artistId
	 * @return
	 * @author Daniel Orlando López Ochoa
	 */
	public ResponseEntity<ResponseWrapper<List<GenreDto>>> getGenreFrequencyByArtis(Integer artistId);
}
