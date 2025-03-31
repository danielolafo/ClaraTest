package com.clara.test.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.clara.test.dto.LabelDto;
import com.clara.test.dto.ResponseWrapper;
import com.clara.test.entity.Label;
import com.clara.test.mapper.LabelMapper;
import com.clara.test.repository.LabelRepository;
import com.clara.test.service.LabelService;

import lombok.NonNull;
@Service
public class LabelServiceImpl implements LabelService {
	
	@NonNull
	private LabelRepository repository;
	
	public LabelServiceImpl(LabelRepository repository) {
		this.repository = repository;
	}

	@Override
	public ResponseEntity<ResponseWrapper<LabelDto>> insert(LabelDto labelDto) {
		Optional<Label> labelOpt = this.repository.findByLabelName(labelDto.getLabelName());
		if(labelOpt.isPresent()) {
			return new ResponseEntity<>(
					ResponseWrapper.<LabelDto>builder()
					.data(LabelDto.builder().build())
					.build(),
					HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		Integer id = this.repository.save(LabelMapper.INSTANCE.toEntity(labelDto)).getId();
		labelDto.setId(id);
		return new ResponseEntity<>(
				ResponseWrapper.<LabelDto>builder()
				.data(labelDto)
				.build(),
				HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseWrapper<LabelDto>> findByName(LabelDto labelDto) {
		Optional<Label> labelOpt = this.repository.findByLabelName(labelDto.getLabelName());
		if(labelOpt.isEmpty()) {
			return new ResponseEntity<>(
					ResponseWrapper.<LabelDto>builder()
					.data(LabelDto.builder().build())
					.build(),
					HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(
				ResponseWrapper.<LabelDto>builder()
				.data(LabelMapper.INSTANCE.toDto(labelOpt.get()))
				.build(),
				HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseWrapper<List<LabelDto>>> getLabelFrequencyByArtis(Integer artistId) {
		// TODO Auto-generated method stub
		return null;
	}

}
