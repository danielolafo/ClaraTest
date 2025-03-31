package com.clara.test.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.clara.test.dto.LabelDto;
import com.clara.test.entity.Label;

@Mapper
public interface LabelMapper {
	
	LabelMapper INSTANCE = Mappers.getMapper(LabelMapper.class);

	Label toEntity(LabelDto labelDto);
	
	LabelDto toDto(Label label);
}
