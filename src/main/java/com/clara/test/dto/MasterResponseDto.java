package com.clara.test.dto;

import java.util.List;

import com.clara.test.entity.Artist;
import com.clara.test.entity.Image;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * This dto saves information about an album and its tracks and other information aobut It
 * 
 * @author Daniel Orlando López Ochoa
 */
@NoArgsConstructor
@Getter
@Setter
public class MasterResponseDto {

	
	private int id;
	private int main_release;
	private int most_recent_release;
	private String resource_url;
	private String uri;
	private String versions_url;
	private String main_release_url;
	private String most_recent_release_url;
	private int num_for_sale;
	private double lowest_price;
    private List<Image> images;
    private List<String> genres;
    private List<String> styles;
    private int year;
    private List<TracklistDto> tracklist;
    private List<Artist> artists;
    private String title;
    private String data_quality;
}
