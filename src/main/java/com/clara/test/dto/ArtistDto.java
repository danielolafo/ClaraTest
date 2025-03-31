package com.clara.test.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

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
public class ArtistDto {
	private Long id;
	private String name;
	private List<ImageDto> images;
	
	
	private String realName;
	private String profile;
	
	
	private String releasesUrl;
	
	
	private String resourceUrl;
	private List<AliasDto> aliases;
	
	
	private String quality;
	
	private String uri;
	
	
	private List<ReleaseDto> releases;
	
	private List<ArtistReleaseDto> artistReleases;
	
	private List<MemberDto> memebers;
	
	@JsonProperty("genres")
	private List<String> lstGenres;
	
	@JsonProperty("tags")
	private List<String> lstTags;
	
	private Integer yearsActive;
}
