package com.clara.test.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ArtistResponseDto {

	private Long id;
	private String name;
	private List<ImageDto> images;
	
	@JsonProperty("realname")
	private String realName;
	private String profile;
	
	@JsonProperty("releases_url")
	private String releasesUrl;
	
	@JsonProperty("resource_url")
	private String resourceUrl;
	private List<AliasDto> aliases;
	
	@JsonProperty("data_quality")
	private String quality;
	
	private String uri;
}
