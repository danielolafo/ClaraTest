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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name="ARTIST")
public class Artist {

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
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String resourceUrl;

    @Column(nullable = false, length = 500)
    private String uri;

    @Column(nullable = false, length = 100)
    private String releasesUrl;

    @Column(length = 100)
    private String realName;

    @Column(nullable = false, length = 10000)
    private String artistProfile;

    @Column(nullable = false, length = 100)
    private String dataQuality;

    @OneToMany(mappedBy = "artist")
    private Set<ArtistRelease> artistArtistReleases;
    
    @OneToMany(mappedBy = "artist")
    private Set<Member> artistMembers;
}
