package com.clara.test.service.impl;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.clara.test.dto.ArtistResponseDto;
import com.clara.test.dto.ResponseWrapper;
import com.clara.test.entity.Artist;
import com.clara.test.mapper.ArtistMapper;
import com.clara.test.repository.ArtistRepository;
import com.clara.test.service.ArtistService;

import lombok.NonNull;

@Service
public class ArtistServiceImpl implements ArtistService {
	
	@NonNull
	private ArtistRepository artistRepository;
	
	public ArtistServiceImpl(ArtistRepository artistRepository) {
		this.artistRepository = artistRepository;
	}

	@Override
	public ResponseEntity<ResponseWrapper<ArtistResponseDto>> getArtist(ArtistResponseDto artistResponseDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<ResponseWrapper<ArtistResponseDto>> insert(ArtistResponseDto artistResponseDto) {
		artistResponseDto.setId(null);
		Artist artist = this.artistRepository.save(ArtistMapper.INSTANCE.toArtist(artistResponseDto));
		artistResponseDto.setId(artist.getId());
		return new ResponseEntity<ResponseWrapper<ArtistResponseDto>>(
				ResponseWrapper.<ArtistResponseDto>builder()
				.data(artistResponseDto)
				.message(HttpStatus.OK.getReasonPhrase())
				.status(HttpStatus.OK)
				.build(),
				HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseWrapper<ArtistResponseDto>> findByName(ArtistResponseDto artistResponseDto) {
		Optional<Artist> artistOpt = this.artistRepository.findByName(artistResponseDto.getName());
		if(artistOpt.isPresent()) {
			return new ResponseEntity<ResponseWrapper<ArtistResponseDto>>(
					ResponseWrapper.<ArtistResponseDto>builder()
					.data(artistResponseDto)
					.message(HttpStatus.OK.getReasonPhrase())
					.status(HttpStatus.OK)
					.build(),
					HttpStatus.OK);
		}else {
			return new ResponseEntity<ResponseWrapper<ArtistResponseDto>>(
					ResponseWrapper.<ArtistResponseDto>builder()
					.data(ArtistResponseDto.builder().build())
					.message(HttpStatus.NOT_FOUND.getReasonPhrase())
					.status(HttpStatus.NOT_FOUND)
					.build(),
					HttpStatus.NOT_FOUND);
		}
	}

}
