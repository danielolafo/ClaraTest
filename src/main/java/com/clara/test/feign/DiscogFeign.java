package com.clara.test.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.clara.test.dto.ArtistDiscogResponseDto;
import com.clara.test.dto.ArtistResponseDto;
import com.clara.test.dto.MasterResponseDto;

@FeignClient(name="discog-api", url="${external.api.discog}")
public interface DiscogFeign {
	
	@GetMapping("/database/search")
	public ResponseEntity<ArtistDiscogResponseDto> getArtist(
			@RequestParam(value="artist", required=false) String artist,
			@RequestParam(value="title", required=false) String title,
			@RequestParam(value="release_title", required=false) String releaseTitle,
			@RequestParam("token") String token);
	
	@GetMapping("/masters/{id}")
	public ResponseEntity<MasterResponseDto> getMaster(
			@PathVariable("id") Integer masterId);
	
	@GetMapping("/artists/{id}")
	public ResponseEntity<ArtistResponseDto> getArtistById(
			@PathVariable("id") Long artistId);
	
	@GetMapping("/artists/{id}/release")
	public ResponseEntity<ArtistResponseDto> getArtistReleases(
			@PathVariable("id") Long artistId);

}
