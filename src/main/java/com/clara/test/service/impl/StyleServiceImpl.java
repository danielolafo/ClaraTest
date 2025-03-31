package com.clara.test.service.impl;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.clara.test.dto.LabelDto;
import com.clara.test.dto.ResponseWrapper;
import com.clara.test.dto.StyleDto;
import com.clara.test.entity.Label;
import com.clara.test.entity.Style;
import com.clara.test.mapper.LabelMapper;
import com.clara.test.mapper.StyleMapper;
import com.clara.test.repository.StyleRepository;
import com.clara.test.service.StyleService;

import lombok.NonNull;
@Service
public class StyleServiceImpl implements StyleService {
	
	@NonNull
	private StyleRepository repository;
	
	public StyleServiceImpl(StyleRepository repository) {
		this.repository = repository;
	}

	@Override
	public ResponseEntity<ResponseWrapper<StyleDto>> insert(StyleDto styleDto) {
		Optional<Style> styleOpt = this.repository.findByStyleName(styleDto.getStyleName());
		if(styleOpt.isPresent()) {
			return new ResponseEntity<>(
					ResponseWrapper.<StyleDto>builder()
					.data(StyleDto.builder().build())
					.build(),
					HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		Integer id = this.repository.save(StyleMapper.INSTANCE.toEntity(styleDto)).getId();
		styleDto.setId(id);
		return new ResponseEntity<>(
				ResponseWrapper.<StyleDto>builder()
				.data(styleDto)
				.build(),
				HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseWrapper<StyleDto>> findByName(StyleDto styleDto) {
		Optional<Style> styleOpt = this.repository.findByStyleName(styleDto.getStyleName());
		if(styleOpt.isEmpty()) {
			return new ResponseEntity<>(
					ResponseWrapper.<StyleDto>builder()
					.data(StyleDto.builder().build())
					.build(),
					HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(
				ResponseWrapper.<StyleDto>builder()
				.data(StyleMapper.INSTANCE.toDto(styleOpt.get()))
				.build(),
				HttpStatus.OK);
	}

}
