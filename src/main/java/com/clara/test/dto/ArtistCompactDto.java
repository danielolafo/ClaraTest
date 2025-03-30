package com.clara.test.dto;

import java.util.List;

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
public class ArtistCompactDto {
	private String name;
	private String firstReleaseYear;
	private String lastReleaseYear;
	private List<ReleaseDto> releases;
	private Integer activeYears;
	private List<String> genres;
	private List<String> tags;

}
