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
@Builder
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
	
	@JsonProperty("master_id")
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
