package com.clara.test.dto;

import java.util.Set;

import com.clara.test.entity.ReleaseGenre;

import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GenreDto {
	private Integer id;

    private String genreName;
    
    @OneToMany(mappedBy = "genre")
    private Set<ReleaseGenre> genreReleaseGenres;
}
