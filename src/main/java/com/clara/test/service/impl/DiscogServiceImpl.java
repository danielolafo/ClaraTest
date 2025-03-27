package com.clara.test.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.clara.test.dto.ArtistRequestDto;
import com.clara.test.dto.ArtistResponseDto;
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
	public ResponseEntity<ResponseWrapper<ArtistResponseDto>> getArtist(
			ArtistRequestDto artistRequestDto) {
		return discogFeign.getArtist(artistRequestDto.getArtist(), artistRequestDto.getTitle(), artistRequestDto.getReleaseTitle(), artistRequestDto.getToken());
	}

	
}
