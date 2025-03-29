package com.clara.test.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.clara.test.dto.ArtistReleaseDto;
import com.clara.test.entity.ArtistRelease;

@Mapper
public interface ArtistReleaseMapper {
	
	ArtistReleaseMapper INSTANCE = Mappers.getMapper(ArtistReleaseMapper.class);
	
	@Mapping(target="artist", ignore=true)
	public ArtistRelease toEntity(ArtistReleaseDto artistReleaseDto);
	
	@Mapping(target="artist", ignore=true)
	public ArtistReleaseDto toDto(ArtistRelease artistRelease);

}
