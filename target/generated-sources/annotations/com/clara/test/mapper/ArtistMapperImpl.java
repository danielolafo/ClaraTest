package com.clara.test.mapper;

import com.clara.test.dto.ArtistResponseDto;
import com.clara.test.entity.Artist;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-31T09:39:21-0500",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.37.0.v20240215-1558, environment: Java 17.0.11 (Eclipse Adoptium)"
)
public class ArtistMapperImpl implements ArtistMapper {

    @Override
    public Artist toArtist(ArtistResponseDto artistResponseDto) {
        if ( artistResponseDto == null ) {
            return null;
        }

        Artist.ArtistBuilder artist = Artist.builder();

        artist.artistProfile( artistResponseDto.getProfile() );
        artist.dataQuality( artistResponseDto.getQuality() );
        artist.id( artistResponseDto.getId() );
        artist.name( artistResponseDto.getName() );
        artist.realName( artistResponseDto.getRealName() );
        artist.releasesUrl( artistResponseDto.getReleasesUrl() );
        artist.resourceUrl( artistResponseDto.getResourceUrl() );
        artist.uri( artistResponseDto.getUri() );

        return artist.build();
    }

    @Override
    public ArtistResponseDto toDto(Artist artist) {
        if ( artist == null ) {
            return null;
        }

        ArtistResponseDto.ArtistResponseDtoBuilder artistResponseDto = ArtistResponseDto.builder();

        artistResponseDto.profile( artist.getArtistProfile() );
        artistResponseDto.quality( artist.getDataQuality() );
        artistResponseDto.id( artist.getId() );
        artistResponseDto.name( artist.getName() );
        artistResponseDto.realName( artist.getRealName() );
        artistResponseDto.releasesUrl( artist.getReleasesUrl() );
        artistResponseDto.resourceUrl( artist.getResourceUrl() );
        artistResponseDto.uri( artist.getUri() );

        return artistResponseDto.build();
    }
}
