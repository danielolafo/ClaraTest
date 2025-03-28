package com.clara.test.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TracklistDto {

	private String position;
	private String type_;
	private String title;
	private List<ExtraArtistDto> extraartists;
	private String duration;
	
}
