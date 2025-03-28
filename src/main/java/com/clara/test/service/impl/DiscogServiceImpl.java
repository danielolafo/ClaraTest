package com.clara.test.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.clara.test.dto.ArtistDiscogResponseDto;
import com.clara.test.dto.ArtistRequestDto;
import com.clara.test.dto.ResponseWrapper;
import com.clara.test.feign.DiscogFeign;
import com.clara.test.service.DiscogService;

import lombok.NonNull;

@Service
public class DiscogServiceImpl implements DiscogService {
	
	@NonNull
	private DiscogFeign discogFeign;
	
	public DiscogServiceImpl(DiscogFeign discogFeign) {
		this.discogFeign = discogFeign;
	}

	@Override
	public ResponseEntity<ResponseWrapper<ArtistDiscogResponseDto>> getArtist(
			ArtistRequestDto artistRequestDto) {
		ResponseEntity<ArtistDiscogResponseDto> discogResp =discogFeign.getArtist(artistRequestDto.getArtist(), artistRequestDto.getTitle(), artistRequestDto.getReleaseTitle(), artistRequestDto.getToken());
		return new ResponseEntity<>(
				ResponseWrapper.<ArtistDiscogResponseDto>builder()
				.data(discogResp.getBody())
				.message(discogResp.getStatusCode().is2xxSuccessful() ? "OK" : "No results found")
				.status(HttpStatus.valueOf(discogResp.getStatusCodeValue()))
				.build(),
				discogResp.getStatusCode());
	}

	
}
