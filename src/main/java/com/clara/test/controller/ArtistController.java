package com.clara.test.controller;

import java.awt.print.Book;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clara.test.dto.ArtistComparissonRequestDto;
import com.clara.test.dto.ArtistComparissonResponseDto;
import com.clara.test.dto.ArtistDiscogResponseDto;
import com.clara.test.dto.ArtistDto;
import com.clara.test.dto.ArtistRequestDto;
import com.clara.test.dto.DiscographyRequestDto;
import com.clara.test.dto.ResponseWrapper;
import com.clara.test.exception.DatabaseException;
import com.clara.test.exception.InvalidValueException;
import com.clara.test.service.DiscogService;

import io.micrometer.common.lang.NonNull;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/artist")
public class ArtistController {
	
	@NonNull
	private DiscogService discogService;
	
	public ArtistController(DiscogService discogService) {
		this.discogService = discogService;
	}
	
	@Operation(summary = "Get an artist searching by its name")
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "Artist found", 
	    content = { @Content(mediaType = "application/json", 
	      schema = @Schema(implementation = ArtistDiscogResponseDto.class)) }),
	  @ApiResponse(responseCode = "404", description = "Artist not found", 
	    content = @Content) })
	@GetMapping("/get")
	public ResponseEntity<ResponseWrapper<ArtistDiscogResponseDto>> getArtist(
			@RequestParam(value="artist", required=false) String artist,
			@RequestParam(value="title", required=false) String title,
			@RequestParam(value="release_title", required=false) String releaseTitle,
			@ModelAttribute ArtistRequestDto artistRequestDto) throws InvalidValueException, DatabaseException{
		
		return discogService.getArtist(artistRequestDto);
	}
	
	
	@Operation(summary = "Receive a list of artists and make a comparisson between artist discography")
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "Artist comparisson data", 
	    content = { @Content(mediaType = "application/json", 
	      schema = @Schema(implementation = ArtistComparissonResponseDto.class)) }),
	  @ApiResponse(responseCode = "400", description = "Not enough data for make the comparisson", 
	    content = @Content) })
	@PostMapping("/compare-artists")
	public ResponseEntity<ResponseWrapper<ArtistComparissonResponseDto>> compareArtist(
			@RequestBody ArtistComparissonRequestDto artistRequestDto){
		
		return discogService.compareArtists(artistRequestDto);
	}
	
	@Operation(summary = "Get the discography of an artist")
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "Artist disocgraphy data found", 
	    content = { @Content(mediaType = "application/json", 
	      schema = @Schema(implementation = ArtistComparissonResponseDto.class)) }),
	  @ApiResponse(responseCode = "404", description = "Not foudn artist discography", 
	    content = @Content) })
	@GetMapping("/get-discography/{artistId}")
	public ResponseEntity<ResponseWrapper<ArtistDto>> getDiscography(
			@PathVariable("artistId") Integer artistId,
			@RequestParam("page") Integer page,
			@RequestParam("pageSize") Integer pageSize,
			@ModelAttribute DiscographyRequestDto discographyRequestDto){
		return this.discogService.getDiscography(discographyRequestDto);
	}

}
