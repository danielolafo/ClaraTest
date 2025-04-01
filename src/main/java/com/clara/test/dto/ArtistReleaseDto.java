package com.clara.test.dto;

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
public class ArtistReleaseDto {

	private Integer id;
	private String title;
	private String type;
	private int main_release;
	private String artist;
	private String role;
	private String resource_url;
	private int year;
	private String thumb;
	private StatDto stats;
    private ArtistResponseDto artistResponseDto;
    private ReleaseDto release;
	
}
