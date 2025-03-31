package com.clara.test.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.clara.test.dto.ArtistDto;
import com.clara.test.dto.ArtistResponseDto;
import com.clara.test.entity.Artist;

@Mapper
public interface ArtistMapper {
	
	ArtistMapper INSTANCE = Mappers.getMapper(ArtistMapper.class);
	
	@Mapping(source="profile", target="artistProfile")
	@Mapping(source="quality", target="dataQuality")
	//@Mapping(source="", target="")
	public Artist toArtist(ArtistResponseDto artistResponseDto);
	
	@InheritInverseConfiguration
	public ArtistResponseDto toDto(Artist artist);
	
	public ArtistDto toDto(ArtistResponseDto artistResponseDto);

}
