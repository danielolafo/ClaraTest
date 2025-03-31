package com.clara.test.service.impl;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.clara.test.dto.GenreDto;
import com.clara.test.dto.ResponseWrapper;
import com.clara.test.entity.Genre;
import com.clara.test.mapper.GenreMapper;
import com.clara.test.repository.GenreRepository;
import com.clara.test.service.GenreService;

import lombok.NonNull;

@Service
public class GenreServiceImpl implements GenreService {
	
	@NonNull
	private GenreRepository repository;
	
	public GenreServiceImpl(GenreRepository repository) {
		this.repository = repository;
	}

	@Override
	public ResponseEntity<ResponseWrapper<GenreDto>> insert(GenreDto genreDto) {
		Optional<Genre> genreOpt = this.repository.findByGenreName(genreDto.getGenreName());
		
		if(genreOpt.isPresent()) {
			//Throw exception
		}
		Integer genreId = this.repository.save(GenreMapper.INSTANCE.toEntity(genreDto)).getId();
		genreDto.setId(genreId);
		return new ResponseEntity<>(
				ResponseWrapper.<GenreDto>builder()
				.data(genreDto)
				.build(),
				HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseWrapper<GenreDto>> findByName(GenreDto genreDto) {
		Optional<Genre> genreOpt = this.repository.findByGenreName(genreDto.getGenreName());
		
		if(genreOpt.isEmpty()) {
			return new ResponseEntity<>(
					ResponseWrapper.<GenreDto>builder()
					.data(GenreDto.builder().build())
					.build(),
					HttpStatus.NOT_FOUND);
		}
		genreDto.setId(genreOpt.get().getId());
		return new ResponseEntity<>(
				ResponseWrapper.<GenreDto>builder()
				.data(genreDto)
				.build(),
				HttpStatus.OK);
	}

}
