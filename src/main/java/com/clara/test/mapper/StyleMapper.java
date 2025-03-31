package com.clara.test.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.clara.test.dto.StyleDto;
import com.clara.test.entity.Style;

@Mapper
public interface StyleMapper {
	
	StyleMapper INSTANCE = Mappers.getMapper(StyleMapper.class);

	Style toEntity(StyleDto styleDto);

	StyleDto toDto(Style style);
}
