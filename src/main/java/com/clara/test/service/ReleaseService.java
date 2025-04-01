package com.clara.test.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.clara.test.dto.DiscographyRequestDto;
import com.clara.test.dto.ReleaseDto;
import com.clara.test.dto.ResponseWrapper;

public interface ReleaseService {

	/**
	 * 
	 * @param releaseDto
	 * @return
	 * @author Daniel Orlando López Ochoa
	 */
	public ResponseEntity<ResponseWrapper<ReleaseDto>> insert(ReleaseDto releaseDto);
	
	
	/**
	 * <p>Create several new releases from the list</p>
	 * @param lstReleaseDtos
	 * @return
	 * @author Daniel Orlando López Ochoa
	 */
	public ResponseEntity<ResponseWrapper<List<ReleaseDto>>> insert(List<ReleaseDto> lstReleaseDtos);
	
	
	/**
	 * 
	 * @param artistId
	 * @return
	 * @author Daniel Orlando López Ochoa
	 */
	public ResponseEntity<ResponseWrapper<List<ReleaseDto>>> getReleasesByArtist(Integer artistId);
	
	/**
	 * <p>Save all discography. If a release exists in database doesn't save It</p>
	 * @param lstReleaseDtos
	 * @return
	 * @author Daniel Orlando López Ochoa
	 */
	public ResponseEntity<ResponseWrapper<List<ReleaseDto>>> save(List<ReleaseDto> lstReleaseDtos);
	
	
	/**
	 * <p>Create/update a release</p>
	 * @param releaseDto
	 * @return
	 * @author Daniel Orlando López Ochoa
	 */
	public ResponseEntity<ResponseWrapper<ReleaseDto>> save(ReleaseDto releaseDto);
	
	
	/**
	 * <p>Get all discography(releases) associated to an artist</p>
	 * @param discographyRequestDto
	 * @return
	 * @author Daniel Orlando López Ochoa
	 */
	public ResponseEntity<ResponseWrapper<List<ReleaseDto>>> getDiscography(DiscographyRequestDto discographyRequestDto);
}
