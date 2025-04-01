package com.clara.test.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.clara.test.dto.StyleDto;
import com.clara.test.entity.Style;

@Mapper
public interface StyleMapper {
	
	StyleMapper INSTANCE = Mappers.getMapper(StyleMapper.class);

	@Mapping(target="styleReleaseStyles", ignore=true)
	Style toEntity(StyleDto styleDto);

	@Mapping(target="styleReleaseStyles", ignore=true)
	StyleDto toDto(Style style);
}
