package com.clara.test.service.impl;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.clara.test.dto.ReleaseGenreDto;
import com.clara.test.dto.ResponseWrapper;
import com.clara.test.entity.ReleaseGenre;
import com.clara.test.mapper.ReleaseGenreMapper;
import com.clara.test.repository.ReleaseGenreRepository;
import com.clara.test.service.ReleaseGenreService;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
public class ReleaseGenreServiceImpl implements ReleaseGenreService {
	
	@NonNull
	private ReleaseGenreRepository repository;
	
	public ReleaseGenreServiceImpl(ReleaseGenreRepository repository) {
		this.repository = repository;
	}

	@Override
	public ResponseEntity<ResponseWrapper<ReleaseGenreDto>> insert(ReleaseGenreDto releaseGenreDto) {
		log.info("{} releaseGenreDto: {}", Thread.currentThread().getStackTrace()[1].getMethodName(),releaseGenreDto);
		Optional<ReleaseGenre> releaseGenreOpt = this.repository.findByGenreAndRelease(releaseGenreDto.getRelease().getId(), releaseGenreDto.getGenre().getId());
		if(releaseGenreOpt.isPresent()) {
			//Throw exception
		}
		Integer id = this.repository.save(ReleaseGenreMapper.INSTANCE.toEntity(releaseGenreDto)).getId();
		releaseGenreDto.setId(id);
		return new ResponseEntity<>(
				ResponseWrapper.<ReleaseGenreDto>builder().data(releaseGenreDto)
				.build(),
				HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseWrapper<ReleaseGenreDto>> findByReleaseAndGenre(Integer releaseId, Integer genreIds) {
		log.info("{} releaseId: {}, genreIds: {}", Thread.currentThread().getStackTrace()[1].getMethodName(),releaseId, genreIds);
		Optional<ReleaseGenre> releaseGenreOpt = this.repository.findByGenreAndRelease(releaseId, genreIds);
		if(releaseGenreOpt.isEmpty()) {
			return new ResponseEntity<>(
					ResponseWrapper.<ReleaseGenreDto>builder().data(ReleaseGenreDto.builder().build())
					.build(),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(
				ResponseWrapper.<ReleaseGenreDto>builder().data(ReleaseGenreMapper.INSTANCE.toDto(releaseGenreOpt.get()))
				.build(),
				HttpStatus.OK);
	}

}
