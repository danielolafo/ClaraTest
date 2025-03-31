package com.clara.test.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.clara.test.dto.ReleaseStyleDto;
import com.clara.test.entity.ReleaseStyle;

@Mapper
public interface ReleaseStyleMapper {
	
	ReleaseStyleMapper INSTANCE = Mappers.getMapper(ReleaseStyleMapper.class);

	ReleaseStyle toEntity(ReleaseStyleDto releaseStyleDto);

	ReleaseStyleDto toDto(ReleaseStyle releaseStyle);
}
