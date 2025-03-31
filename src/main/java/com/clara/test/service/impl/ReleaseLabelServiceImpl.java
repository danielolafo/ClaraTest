package com.clara.test.service.impl;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.clara.test.dto.ReleaseLabelDto;
import com.clara.test.dto.ResponseWrapper;
import com.clara.test.entity.ReleaseLabel;
import com.clara.test.mapper.ReleaseLabelMapper;
import com.clara.test.repository.ReleaseLabelRepository;
import com.clara.test.service.ReleaseLabelService;

import lombok.NonNull;
@Service
public class ReleaseLabelServiceImpl implements ReleaseLabelService{
	
	@NonNull
	private ReleaseLabelRepository repository;
	
	public ReleaseLabelServiceImpl(ReleaseLabelRepository repository) {
		this.repository = repository;
	}

	@Override
	public ResponseEntity<ResponseWrapper<ReleaseLabelDto>> insert(ReleaseLabelDto releaseLabelDto) {
		Optional<ReleaseLabel> releaseLabelOpt = this.repository.findByReleaseAndLabel(releaseLabelDto.getRelease().getId(), releaseLabelDto.getLabel().getId());
		if(releaseLabelOpt.isPresent()) {
			return new ResponseEntity<>(
					ResponseWrapper.<ReleaseLabelDto>builder().data(ReleaseLabelDto.builder().build())
					.build(),
					HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		Integer id = this.repository.save(ReleaseLabelMapper.INSTANCE.toEntity(releaseLabelDto)).getId();
		releaseLabelDto.setId(id);
		return new ResponseEntity<>(
				ResponseWrapper.<ReleaseLabelDto>builder().data(releaseLabelDto)
				.build(),
				HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseWrapper<ReleaseLabelDto>> findByReleaseAndLabel(Integer releaseId, Integer labelId) {
		Optional<ReleaseLabel> releaseLabelOpt = this.repository.findByReleaseAndLabel(releaseId, labelId);
		if(releaseLabelOpt.isEmpty()) {
			return new ResponseEntity<>(
					ResponseWrapper.<ReleaseLabelDto>builder().data(ReleaseLabelDto.builder().build())
					.build(),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(
				ResponseWrapper.<ReleaseLabelDto>builder().data(ReleaseLabelMapper.INSTANCE.toDto(releaseLabelOpt.get()))
				.build(),
				HttpStatus.OK);
	}

}
