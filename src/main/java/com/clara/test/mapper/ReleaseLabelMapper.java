package com.clara.test.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.clara.test.dto.ReleaseLabelDto;
import com.clara.test.entity.ReleaseLabel;

@Mapper
public interface ReleaseLabelMapper {
	
	ReleaseLabelMapper INSTANCE = Mappers.getMapper(ReleaseLabelMapper.class);

	ReleaseLabel toEntity(ReleaseLabelDto releaseLabelDto);

	ReleaseLabelDto toDto(ReleaseLabel releaseLabel);
}
