package com.clara.test.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ArtistRequestDto {

	private String artist;
	private String title;
	private String releaseTitle;
	private String token;
}
