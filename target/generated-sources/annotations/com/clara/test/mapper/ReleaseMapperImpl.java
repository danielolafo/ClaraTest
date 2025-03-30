package com.clara.test.mapper;

import com.clara.test.dto.CommunityDto;
import com.clara.test.dto.ReleaseDto;
import com.clara.test.dto.ResultDto;
import com.clara.test.entity.ArtistRelease;
import com.clara.test.entity.Barcode;
import com.clara.test.entity.Community;
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
    date = "2025-03-30T12:35:27-0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.11 (Eclipse Adoptium)"
)
public class ReleaseMapperImpl implements ReleaseMapper {

    @Override
    public Release toEntity(ReleaseDto releaseDto) {
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
        Set<Style> set = releaseDto.getReleaseStyles();
        if ( set != null ) {
            release.setReleaseStyles( new LinkedHashSet<Style>( set ) );
        }
        Set<Barcode> set1 = releaseDto.getReleaseBarcodes();
        if ( set1 != null ) {
            release.setReleaseBarcodes( new LinkedHashSet<Barcode>( set1 ) );
        }
        Set<Genre> set2 = releaseDto.getReleaseGenres();
        if ( set2 != null ) {
            release.setReleaseGenres( new LinkedHashSet<Genre>( set2 ) );
        }
        Set<Format> set3 = releaseDto.getReleaseFormats();
        if ( set3 != null ) {
            release.setReleaseFormats( new LinkedHashSet<Format>( set3 ) );
        }
        Set<Label> set4 = releaseDto.getReleaseLabels();
        if ( set4 != null ) {
            release.setReleaseLabels( new LinkedHashSet<Label>( set4 ) );
        }
        Set<Track> set5 = releaseDto.getReleaseTracks();
        if ( set5 != null ) {
            release.setReleaseTracks( new LinkedHashSet<Track>( set5 ) );
        }
        Set<ArtistRelease> set6 = releaseDto.getReleaseArtistReleases();
        if ( set6 != null ) {
            release.setReleaseArtistReleases( new LinkedHashSet<ArtistRelease>( set6 ) );
        }

        return release;
    }

    @Override
    public ReleaseDto toDto(Release release) {
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
        Set<Style> set = release.getReleaseStyles();
        if ( set != null ) {
            releaseDto.setReleaseStyles( new LinkedHashSet<Style>( set ) );
        }
        Set<Barcode> set1 = release.getReleaseBarcodes();
        if ( set1 != null ) {
            releaseDto.setReleaseBarcodes( new LinkedHashSet<Barcode>( set1 ) );
        }
        Set<Genre> set2 = release.getReleaseGenres();
        if ( set2 != null ) {
            releaseDto.setReleaseGenres( new LinkedHashSet<Genre>( set2 ) );
        }
        Set<Format> set3 = release.getReleaseFormats();
        if ( set3 != null ) {
            releaseDto.setReleaseFormats( new LinkedHashSet<Format>( set3 ) );
        }
        Set<Label> set4 = release.getReleaseLabels();
        if ( set4 != null ) {
            releaseDto.setReleaseLabels( new LinkedHashSet<Label>( set4 ) );
        }
        Set<Track> set5 = release.getReleaseTracks();
        if ( set5 != null ) {
            releaseDto.setReleaseTracks( new LinkedHashSet<Track>( set5 ) );
        }
        Set<ArtistRelease> set6 = release.getReleaseArtistReleases();
        if ( set6 != null ) {
            releaseDto.setReleaseArtistReleases( new LinkedHashSet<ArtistRelease>( set6 ) );
        }

        return releaseDto;
    }

    @Override
    public ReleaseDto toDto(ResultDto resultDto) {
        if ( resultDto == null ) {
            return null;
        }

        ReleaseDto releaseDto = new ReleaseDto();

        releaseDto.setId( resultDto.getId() );
        releaseDto.setCountry( resultDto.getCountry() );
        releaseDto.setMasterId( resultDto.getMasterId() );
        releaseDto.setMasterUrl( resultDto.getMasterUrl() );
        releaseDto.setUri( resultDto.getUri() );
        releaseDto.setCatno( resultDto.getCatno() );
        releaseDto.setTitle( resultDto.getTitle() );
        releaseDto.setThumb( resultDto.getThumb() );
        releaseDto.setCoverImage( resultDto.getCoverImage() );
        releaseDto.setResourceUrl( resultDto.getResourceUrl() );
        releaseDto.setCommunity( communityDtoToCommunity( resultDto.getCommunity() ) );

        return releaseDto;
    }

    protected Community communityDtoToCommunity(CommunityDto communityDto) {
        if ( communityDto == null ) {
            return null;
        }

        Community community = new Community();

        community.setWant( communityDto.getWant() );
        community.setHave( communityDto.getHave() );

        return community;
    }
}
