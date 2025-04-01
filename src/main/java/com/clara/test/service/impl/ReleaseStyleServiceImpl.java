package com.clara.test.service.impl;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.clara.test.dto.ReleaseStyleDto;
import com.clara.test.dto.ResponseWrapper;
import com.clara.test.entity.ReleaseStyle;
import com.clara.test.mapper.ReleaseStyleMapper;
import com.clara.test.repository.ReleaseStyleRepository;
import com.clara.test.service.ReleaseStyleService;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
public class ReleaseStyleServiceImpl implements ReleaseStyleService {
	
	@NonNull
	private ReleaseStyleRepository repository;
	
	public ReleaseStyleServiceImpl(ReleaseStyleRepository repository) {
		this.repository = repository;
	}

	@Override
	public ResponseEntity<ResponseWrapper<ReleaseStyleDto>> insert(ReleaseStyleDto releaseStyleDto) {
		log.info("{} releaseStyleDto: {}", Thread.currentThread().getStackTrace()[1].getMethodName(),releaseStyleDto);
		Optional<ReleaseStyle> releaseStyleOpt = this.repository.findByReleaseAndStyle(releaseStyleDto.getRelease().getId(), releaseStyleDto.getStyle().getId());
		if(releaseStyleOpt.isPresent()) {
			return new ResponseEntity<>(
					ResponseWrapper.<ReleaseStyleDto>builder().data(ReleaseStyleDto.builder().build())
					.build(),
					HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		Integer id = this.repository.save(ReleaseStyleMapper.INSTANCE.toEntity(releaseStyleDto)).getId();
		releaseStyleDto.setId(id);
		return new ResponseEntity<>(
				ResponseWrapper.<ReleaseStyleDto>builder().data(releaseStyleDto)
				.build(),
				HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseWrapper<ReleaseStyleDto>> findByReleaseAndStyle(Integer releaseId, Integer styleId) {
		log.info("{} releaseId: {}, styleId: {}", Thread.currentThread().getStackTrace()[1].getMethodName(),releaseId, styleId);
		Optional<ReleaseStyle> releaseStyleOpt = this.repository.findByReleaseAndStyle(releaseId, styleId);
		if(releaseStyleOpt.isEmpty()) {
			return new ResponseEntity<>(
					ResponseWrapper.<ReleaseStyleDto>builder().data(ReleaseStyleDto.builder().build())
					.build(),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(
				ResponseWrapper.<ReleaseStyleDto>builder().data(ReleaseStyleMapper.INSTANCE.toDto(releaseStyleOpt.get()))
				.build(),
				HttpStatus.OK);
	}

}
