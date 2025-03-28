package com.clara.test.mapper;

import com.clara.test.dto.ArtistResponseDto;
import com.clara.test.entity.Artist;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-28T18:06:23-0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.11 (Eclipse Adoptium)"
)
public class ArtistMapperImpl implements ArtistMapper {

    @Override
    public Artist toArtist(ArtistResponseDto artistResponseDto) {
        if ( artistResponseDto == null ) {
            return null;
        }

        Artist artist = new Artist();

        artist.setArtistProfile( artistResponseDto.getProfile() );
        artist.setDataQuality( artistResponseDto.getQuality() );
        artist.setId( artistResponseDto.getId() );
        artist.setName( artistResponseDto.getName() );
        artist.setResourceUrl( artistResponseDto.getResourceUrl() );
        artist.setUri( artistResponseDto.getUri() );
        artist.setReleasesUrl( artistResponseDto.getReleasesUrl() );
        artist.setRealName( artistResponseDto.getRealName() );

        return artist;
    }
}
