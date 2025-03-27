package com.clara.test.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ResultDto {

	private String country;
	private String year;
	private List<String> format;
	private List<String> label;
	private String type;
	private List<String> genre;
	private List<String> style;
	private int id;
	private List<String> barcode;
	private UserDataDto user_data;
	private int master_id;
	private String master_url;
	private String uri;
	private String catno;
	private String title;
	private String thumb;
	private String cover_image;
	private String resource_url;
	private CommunityDto community;
}
