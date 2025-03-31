package com.clara.test.mapper;

import com.clara.test.dto.ArtistReleaseDto;
import com.clara.test.dto.ReleaseDto;
import com.clara.test.entity.ArtistRelease;
import com.clara.test.entity.Barcode;
import com.clara.test.entity.Format;
import com.clara.test.entity.Release;
import com.clara.test.entity.Track;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-31T11:02:00-0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.11 (Eclipse Adoptium)"
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

        release.setId( releaseDto.getId() );
        release.setCountry( releaseDto.getCountry() );
        release.setReleaseYear( releaseDto.getReleaseYear() );
        release.setReleaseType( releaseDto.getReleaseType() );
        release.setMasterId( releaseDto.getMasterId() );
        release.setMasterUrl( releaseDto.getMasterUrl() );
        release.setUri( releaseDto.getUri() );
        release.setCatno( releaseDto.getCatno() );
        release.setTitle( releaseDto.getTitle() );
        release.setThumb( releaseDto.getThumb() );
        release.setCoverImage( releaseDto.getCoverImage() );
        release.setResourceUrl( releaseDto.getResourceUrl() );
        release.setMainRelease( releaseDto.getMainRelease() );
        release.setReleaseRolE( releaseDto.getReleaseRolE() );
        release.setCommunity( releaseDto.getCommunity() );
        Set<Barcode> set = releaseDto.getReleaseBarcodes();
        if ( set != null ) {
            release.setReleaseBarcodes( new LinkedHashSet<Barcode>( set ) );
        }
        Set<Format> set1 = releaseDto.getReleaseFormats();
        if ( set1 != null ) {
            release.setReleaseFormats( new LinkedHashSet<Format>( set1 ) );
        }
        Set<Track> set2 = releaseDto.getReleaseTracks();
        if ( set2 != null ) {
            release.setReleaseTracks( new LinkedHashSet<Track>( set2 ) );
        }
        Set<ArtistRelease> set3 = releaseDto.getReleaseArtistReleases();
        if ( set3 != null ) {
            release.setReleaseArtistReleases( new LinkedHashSet<ArtistRelease>( set3 ) );
        }

        return release;
    }

    protected ReleaseDto releaseToReleaseDto(Release release) {
        if ( release == null ) {
            return null;
        }

        ReleaseDto releaseDto = new ReleaseDto();

        releaseDto.setId( release.getId() );
        releaseDto.setCountry( release.getCountry() );
        releaseDto.setReleaseYear( release.getReleaseYear() );
        releaseDto.setReleaseType( release.getReleaseType() );
        releaseDto.setMasterId( release.getMasterId() );
        releaseDto.setMasterUrl( release.getMasterUrl() );
        releaseDto.setUri( release.getUri() );
        releaseDto.setCatno( release.getCatno() );
        releaseDto.setTitle( release.getTitle() );
        releaseDto.setThumb( release.getThumb() );
        releaseDto.setCoverImage( release.getCoverImage() );
        releaseDto.setResourceUrl( release.getResourceUrl() );
        releaseDto.setMainRelease( release.getMainRelease() );
        releaseDto.setReleaseRolE( release.getReleaseRolE() );
        releaseDto.setCommunity( release.getCommunity() );
        Set<Barcode> set = release.getReleaseBarcodes();
        if ( set != null ) {
            releaseDto.setReleaseBarcodes( new LinkedHashSet<Barcode>( set ) );
        }
        Set<Format> set1 = release.getReleaseFormats();
        if ( set1 != null ) {
            releaseDto.setReleaseFormats( new LinkedHashSet<Format>( set1 ) );
        }
        Set<Track> set2 = release.getReleaseTracks();
        if ( set2 != null ) {
            releaseDto.setReleaseTracks( new LinkedHashSet<Track>( set2 ) );
        }
        Set<ArtistRelease> set3 = release.getReleaseArtistReleases();
        if ( set3 != null ) {
            releaseDto.setReleaseArtistReleases( new LinkedHashSet<ArtistRelease>( set3 ) );
        }

        return releaseDto;
    }
}
