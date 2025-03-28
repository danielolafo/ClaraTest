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
	private UserDataDto userData;
	private int masterId;
	private String masterUrl;
	private String uri;
	private String catno;
	private String title;
	private String thumb;
	private String coverImage;
	private String resourceUrl;
	private CommunityDto community;
}
