package com.clara.test.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clara.test.dto.ArtistRequestDto;
import com.clara.test.dto.ArtistResponseDto;
import com.clara.test.dto.ResponseWrapper;
import com.clara.test.service.DiscogService;

import io.micrometer.common.lang.NonNull;

@RestController
@RequestMapping("/artist")
public class ArtistController {
	
	@NonNull
	private DiscogService discogService;
	
	public ArtistController(DiscogService discogService) {
		this.discogService = discogService;
	}
	
	@GetMapping("/get")
	public ResponseEntity<ResponseWrapper<ArtistResponseDto>> getArtist(
			@RequestParam(value="artist", required=false) String artist,
			@RequestParam(value="title", required=false) String title,
			@RequestParam(value="release_title", required=false) String releaseTitle,
			@RequestParam("token") String token,
			@ModelAttribute ArtistRequestDto artistRequestDto){
		
		return discogService.getArtist(artistRequestDto);
	}

}
