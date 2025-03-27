package com.clara.test.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ArtistResponseDto {

	String name;
	List<ImageDto> images;
	private String realName;
	private String profile;
	private String releasesUrl;
	private List<String> aliases;
	private String quality;
}
