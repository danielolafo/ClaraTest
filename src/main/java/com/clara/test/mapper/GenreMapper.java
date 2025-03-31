package com.clara.test.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.clara.test.dto.GenreDto;
import com.clara.test.entity.Genre;

@Mapper
public interface GenreMapper {
	
	GenreMapper INSTANCE = Mappers.getMapper(GenreMapper.class);
	
	Genre toEntity(GenreDto genreDto);
	
	GenreDto toDto(Genre genre);

}
