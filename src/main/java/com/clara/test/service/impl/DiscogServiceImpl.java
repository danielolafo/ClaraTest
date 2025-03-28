package com.clara.test.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.clara.test.dto.ArtistDiscogResponseDto;
import com.clara.test.dto.ArtistRequestDto;
import com.clara.test.dto.ArtistResponseDto;
import com.clara.test.dto.MasterResponseDto;
import com.clara.test.dto.ResponseWrapper;
import com.clara.test.feign.DiscogFeign;
import com.clara.test.service.ArtistService;
import com.clara.test.service.DiscogService;

import lombok.NonNull;

@Service
public class DiscogServiceImpl implements DiscogService {
	
	@NonNull
	private DiscogFeign discogFeign;
	
	@NonNull
	private ArtistService artistService;
	
	public DiscogServiceImpl(
			DiscogFeign discogFeign,
			ArtistService artistService) {
		this.discogFeign = discogFeign;
		this.artistService = artistService;
	}

	@Override
	public ResponseEntity<ResponseWrapper<ArtistDiscogResponseDto>> getArtist(
			ArtistRequestDto artistRequestDto) {
		ResponseEntity<ArtistDiscogResponseDto> discogResp =discogFeign.getArtist(artistRequestDto.getArtist(), artistRequestDto.getTitle(), artistRequestDto.getReleaseTitle(), artistRequestDto.getToken());
		
		//Get the mastersId field
		Integer masterId = discogResp.getBody().getResults().get(0).getMasterId();
		ResponseEntity<MasterResponseDto> masterResponse = discogFeign.getMaster(masterId);
		
		//Extract and get the artist
		ResponseEntity<ArtistResponseDto> artistResponseDto = discogFeign.getArtistById(masterResponse.getBody().getArtists().get(0).getId());
		
		ResponseEntity<ResponseWrapper<ArtistResponseDto>> artistResp = this.artistService.insert(artistResponseDto.getBody());
		
		//Save albums
		
		
		//Save tracks
		
		//Save artist members
		return new ResponseEntity<>(
				ResponseWrapper.<ArtistDiscogResponseDto>builder()
				.data(discogResp.getBody())
				.message(discogResp.getStatusCode().is2xxSuccessful() ? "OK" : "No results found")
				.status(HttpStatus.valueOf(discogResp.getStatusCodeValue()))
				.build(),
				discogResp.getStatusCode());
	}

	
}
