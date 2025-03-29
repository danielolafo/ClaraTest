package com.clara.test.mapper;

import com.clara.test.dto.ArtistReleaseDto;
import com.clara.test.dto.ReleaseDto;
import com.clara.test.entity.ArtistRelease;
import com.clara.test.entity.Barcode;
import com.clara.test.entity.Format;
import com.clara.test.entity.Genre;
import com.clara.test.entity.Label;
import com.clara.test.entity.Release;
import com.clara.test.entity.Style;
import com.clara.test.entity.Track;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-29T17:39:57-0500",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.37.0.v20240215-1558, environment: Java 17.0.11 (Eclipse Adoptium)"
)
public class ArtistReleaseMapperImpl implements ArtistReleaseMapper {

    @Override
    public ArtistRelease toEntity(ArtistReleaseDto artistReleaseDto) {
        if ( artistReleaseDto == null ) {
            return null;
        }

        ArtistRelease.ArtistReleaseBuilder artistRelease = ArtistRelease.builder();

        artistRelease.id( artistReleaseDto.getId() );
        artistRelease.release( releaseDtoToRelease( artistReleaseDto.getRelease() ) );

        return artistRelease.build();
    }

    @Override
    public ArtistReleaseDto toDto(ArtistRelease artistRelease) {
        if ( artistRelease == null ) {
            return null;
        }

        ArtistReleaseDto.ArtistReleaseDtoBuilder artistReleaseDto = ArtistReleaseDto.builder();

        artistReleaseDto.id( artistRelease.getId() );
        artistReleaseDto.release( releaseToReleaseDto( artistRelease.getRelease() ) );

        return artistReleaseDto.build();
    }

    protected Release releaseDtoToRelease(ReleaseDto releaseDto) {
        if ( releaseDto == null ) {
            return null;
        }

        Release release = new Release();

        release.setCatno( releaseDto.getCatno() );
        release.setCommunity( releaseDto.getCommunity() );
        release.setCountry( releaseDto.getCountry() );
        release.setCoverImage( releaseDto.getCoverImage() );
        release.setId( releaseDto.getId() );
        release.setMainRelease( releaseDto.getMainRelease() );
        release.setMasterId( releaseDto.getMasterId() );
        release.setMasterUrl( releaseDto.getMasterUrl() );
        Set<ArtistRelease> set = releaseDto.getReleaseArtistReleases();
        if ( set != null ) {
            release.setReleaseArtistReleases( new LinkedHashSet<ArtistRelease>( set ) );
        }
        Set<Barcode> set1 = releaseDto.getReleaseBarcodes();
        if ( set1 != null ) {
            release.setReleaseBarcodes( new LinkedHashSet<Barcode>( set1 ) );
        }
        Set<Format> set2 = releaseDto.getReleaseFormats();
        if ( set2 != null ) {
            release.setReleaseFormats( new LinkedHashSet<Format>( set2 ) );
        }
        Set<Genre> set3 = releaseDto.getReleaseGenres();
        if ( set3 != null ) {
            release.setReleaseGenres( new LinkedHashSet<Genre>( set3 ) );
        }
        Set<Label> set4 = releaseDto.getReleaseLabels();
        if ( set4 != null ) {
            release.setReleaseLabels( new LinkedHashSet<Label>( set4 ) );
        }
        release.setReleaseRolE( releaseDto.getReleaseRolE() );
        Set<Style> set5 = releaseDto.getReleaseStyles();
        if ( set5 != null ) {
            release.setReleaseStyles( new LinkedHashSet<Style>( set5 ) );
        }
        Set<Track> set6 = releaseDto.getReleaseTracks();
        if ( set6 != null ) {
            release.setReleaseTracks( new LinkedHashSet<Track>( set6 ) );
        }
        release.setReleaseType( releaseDto.getReleaseType() );
        release.setReleaseYear( releaseDto.getReleaseYear() );
        release.setResourceUrl( releaseDto.getResourceUrl() );
        release.setThumb( releaseDto.getThumb() );
        release.setTitle( releaseDto.getTitle() );
        release.setUri( releaseDto.getUri() );

        return release;
    }

    protected ReleaseDto releaseToReleaseDto(Release release) {
        if ( release == null ) {
            return null;
        }

        ReleaseDto releaseDto = new ReleaseDto();

        releaseDto.setCatno( release.getCatno() );
        releaseDto.setCommunity( release.getCommunity() );
        releaseDto.setCountry( release.getCountry() );
        releaseDto.setCoverImage( release.getCoverImage() );
        releaseDto.setId( release.getId() );
        releaseDto.setMainRelease( release.getMainRelease() );
        releaseDto.setMasterId( release.getMasterId() );
        releaseDto.setMasterUrl( release.getMasterUrl() );
        Set<ArtistRelease> set = release.getReleaseArtistReleases();
        if ( set != null ) {
            releaseDto.setReleaseArtistReleases( new LinkedHashSet<ArtistRelease>( set ) );
        }
        Set<Barcode> set1 = release.getReleaseBarcodes();
        if ( set1 != null ) {
            releaseDto.setReleaseBarcodes( new LinkedHashSet<Barcode>( set1 ) );
        }
        Set<Format> set2 = release.getReleaseFormats();
        if ( set2 != null ) {
            releaseDto.setReleaseFormats( new LinkedHashSet<Format>( set2 ) );
        }
        Set<Genre> set3 = release.getReleaseGenres();
        if ( set3 != null ) {
            releaseDto.setReleaseGenres( new LinkedHashSet<Genre>( set3 ) );
        }
        Set<Label> set4 = release.getReleaseLabels();
        if ( set4 != null ) {
            releaseDto.setReleaseLabels( new LinkedHashSet<Label>( set4 ) );
        }
        releaseDto.setReleaseRolE( release.getReleaseRolE() );
        Set<Style> set5 = release.getReleaseStyles();
        if ( set5 != null ) {
            releaseDto.setReleaseStyles( new LinkedHashSet<Style>( set5 ) );
        }
        Set<Track> set6 = release.getReleaseTracks();
        if ( set6 != null ) {
            releaseDto.setReleaseTracks( new LinkedHashSet<Track>( set6 ) );
        }
        releaseDto.setReleaseType( release.getReleaseType() );
        releaseDto.setReleaseYear( release.getReleaseYear() );
        releaseDto.setResourceUrl( release.getResourceUrl() );
        releaseDto.setThumb( release.getThumb() );
        releaseDto.setTitle( release.getTitle() );
        releaseDto.setUri( release.getUri() );

        return releaseDto;
    }
}
