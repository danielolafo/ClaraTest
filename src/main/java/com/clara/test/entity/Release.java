package com.clara.test.entity;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="RELEASE")
public class Release {
	@Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Integer id;

    @Column(length = 50)
    private String country;

    @Column
    private Integer releaseYear;

    @Column(length = 50)
    private String releaseType;

    @Column
    private Integer masterId;

    @Column(length = 100)
    private String masterUrl;

    @Column(length = 100)
    private String uri;

    @Column(length = 50)
    private String catno;

    @Column(length = 100)
    private String title;

    @Column(length = 500)
    private String thumb;

    @Column(length = 500)
    private String coverImage;

    @Column(length = 100)
    private String resourceUrl;

    @Column(length = 100)
    private String mainRelease;

    @Column(length = 100)
    private String releaseRolE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id")
    private Community community;

    @OneToMany(mappedBy = "release")
    private Set<Style> releaseStyles;

    @OneToMany(mappedBy = "release")
    private Set<Barcode> releaseBarcodes;

    @OneToMany(mappedBy = "release")
    private Set<Genre> releaseGenres;

    @OneToMany(mappedBy = "release")
    private Set<Format> releaseFormats;

    @OneToMany(mappedBy = "release")
    private Set<Label> releaseLabels;

    @OneToMany(mappedBy = "release")
    private Set<Track> releaseTracks;

    @OneToMany(mappedBy = "release")
    private Set<ArtistRelease> releaseArtistReleases;
}
