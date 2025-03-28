package com.clara.test.dto;

import java.util.Set;

import com.clara.test.entity.ArtistRelease;
import com.clara.test.entity.Barcode;
import com.clara.test.entity.Community;
import com.clara.test.entity.Format;
import com.clara.test.entity.Genre;
import com.clara.test.entity.Label;
import com.clara.test.entity.Style;
import com.clara.test.entity.Track;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ReleaseDto {
	private Integer id;

	private String country;

	private Integer releaseYear;

	private String releaseType;

	private Integer masterId;

	private String masterUrl;

	private String uri;

	private String catno;

	private String title;

	private String thumb;

	private String coverImage;

	private String resourceUrl;

	private String mainRelease;

	private String releaseRolE;

	private Community community;

	private Set<Style> releaseStyles;

	private Set<Barcode> releaseBarcodes;

	private Set<Genre> releaseGenres;

	private Set<Format> releaseFormats;

	private Set<Label> releaseLabels;

	private Set<Track> releaseTracks;

	private Set<ArtistRelease> releaseArtistReleases;
}
