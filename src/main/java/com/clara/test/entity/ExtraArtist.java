package com.clara.test.entity;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name="EXTRA_ARTIST")
public class ExtraArtist {

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

    @Column(length = 500)
    private String eaName;

    @Column(length = 100)
    private String anv;

    @Column(length = 100)
    private String eaJoin;

    @Column(length = 100)
    private String eaRole;

    @Column(length = 500)
    private String resourceUrl;

    @OneToMany(mappedBy = "ea")
    private Set<ArtistTrack> eaArtistTracks;

    @OneToMany(mappedBy = "extraArtist")
    private Set<Track> extraArtistTracks;
}
