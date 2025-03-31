package com.clara.test.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.clara.test.dto.ReleaseGenreDto;
import com.clara.test.entity.ReleaseGenre;

@Mapper(uses= {GenreMapper.class, ReleaseMapper.class})
public interface ReleaseGenreMapper {
	
	ReleaseGenreMapper INSTANCE = Mappers.getMapper(ReleaseGenreMapper.class);

	ReleaseGenre toEntity(ReleaseGenreDto releaseGenreDto);

	ReleaseGenreDto toDto(ReleaseGenre releaseGenre);
}
