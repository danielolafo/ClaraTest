package com.clara.test.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.clara.test.dto.ReleaseDto;
import com.clara.test.entity.Release;

@Mapper(uses = {ArtistReleaseMapper.class})
public interface ReleaseMapper {
	
	ReleaseMapper INSTANCE = Mappers.getMapper(ReleaseMapper.class);
	
	public Release toEntity(ReleaseDto releaseDto);
	
	public ReleaseDto toDto(Release release);

}
