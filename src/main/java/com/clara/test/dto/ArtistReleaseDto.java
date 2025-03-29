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

	public int id;
    public String title;
    public String type;
    public int main_release;
    public String artist;
    public String role;
    public String resource_url;
    public int year;
    public String thumb;
    public StatDto stats;
    private ArtistResponseDto artistResponseDto;
    private ReleaseDto release;
	
}
