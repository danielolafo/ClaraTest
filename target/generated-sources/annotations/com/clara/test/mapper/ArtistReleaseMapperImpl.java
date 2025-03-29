package com.clara.test.mapper;

import com.clara.test.dto.ArtistReleaseDto;
import com.clara.test.entity.ArtistRelease;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-28T23:24:32-0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.11 (Eclipse Adoptium)"
)
public class ArtistReleaseMapperImpl implements ArtistReleaseMapper {

    @Override
    public ArtistRelease toEntity(ArtistReleaseDto artistReleaseDto) {
        if ( artistReleaseDto == null ) {
            return null;
        }

        ArtistRelease.ArtistReleaseBuilder artistRelease = ArtistRelease.builder();

        artistRelease.id( artistReleaseDto.id );

        return artistRelease.build();
    }

    @Override
    public ArtistReleaseDto toDto(ArtistRelease artistRelease) {
        if ( artistRelease == null ) {
            return null;
        }

        ArtistReleaseDto artistReleaseDto = new ArtistReleaseDto();

        if ( artistRelease.getId() != null ) {
            artistReleaseDto.id = artistRelease.getId();
        }

        return artistReleaseDto;
    }
}
