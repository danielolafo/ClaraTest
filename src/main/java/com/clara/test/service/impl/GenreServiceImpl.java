package com.clara.test.service.impl;

import java.util.ArrayList;
import java.util.List;
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
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GenreServiceImpl implements GenreService {
	
	@NonNull
	private GenreRepository repository;
	
	public GenreServiceImpl(GenreRepository repository) {
		this.repository = repository;
	}

	@Override
	public ResponseEntity<ResponseWrapper<GenreDto>> insert(GenreDto genreDto) {
		log.info("{} genreDto : {}", Thread.currentThread().getStackTrace()[1].getMethodName(), genreDto);
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
		log.info("{} genreDto: {}", Thread.currentThread().getStackTrace()[1].getMethodName(), genreDto);
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

	@Override
	public ResponseEntity<ResponseWrapper<List<GenreDto>>> findByArtist(Integer artistId) {
		log.info("{}  artistId : {}", Thread.currentThread().getStackTrace()[1].getMethodName(), artistId);
		List<GenreDto> lstGenreDto = new ArrayList<>();
		this.repository.findByArtist(artistId).forEach(gen -> lstGenreDto.add(GenreMapper.INSTANCE.toDto(gen)));
		if(lstGenreDto.isEmpty()) {
			return new ResponseEntity<>(
					ResponseWrapper.<List<GenreDto>>builder()
					.data(lstGenreDto)
					.build(),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(
				ResponseWrapper.<List<GenreDto>>builder()
				.data(lstGenreDto)
				.build(),
				HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseWrapper<List<GenreDto>>> getGenreFrequencyByArtis(Integer artistId) {
		log.info("{} artistId : {}", Thread.currentThread().getStackTrace()[1].getMethodName(), artistId);
		List<GenreDto> lstResp = new ArrayList<>();
		this.repository.getGenresFrequencyByArtis(artistId).forEach(gen -> lstResp.add(GenreDto.builder().genreName(gen.getGenreName()).frequency(gen.getFrequency()).build()));
		return new ResponseEntity<>(
				ResponseWrapper.<List<GenreDto>>builder()
				.data(lstResp)
				.build(),
				!lstResp.isEmpty() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
	}
	
	

}
