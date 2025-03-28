package com.clara.test.mapper;

import com.clara.test.dto.ArtistResponseDto;
import com.clara.test.entity.Artist;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-28T23:24:32-0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.11 (Eclipse Adoptium)"
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
        artist.resourceUrl( artistResponseDto.getResourceUrl() );
        artist.uri( artistResponseDto.getUri() );
        artist.releasesUrl( artistResponseDto.getReleasesUrl() );
        artist.realName( artistResponseDto.getRealName() );

        return artist.build();
    }
}
