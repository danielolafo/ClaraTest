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
@Table(name="SONG")
public class Song {
	
	private String name;
	
    public String type_;
    
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

    @Column(nullable = false, length = 100)
    private String trackName;

    @Column(length = 3)
    private String position;

    @Column(length = 100)
    private String type;

    @Column(length = 500)
    private String title;

    @Column(length = 10)
    private String duration;

    @Column
    private Integer extraArtistId;

    @OneToMany(mappedBy = "track")
    private Set<ArtistTrack> trackArtistTracks;

}
