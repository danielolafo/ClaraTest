package com.clara.test.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

import com.clara.test.dto.ArtistResponseDto;
import com.clara.test.dto.ReleaseCollectionDto;
import com.clara.test.dto.ReleaseDto;
import com.clara.test.dto.ResponseWrapper;
import com.clara.test.utils.Webclient;

public class ReleaseWebClient {
	
	public static ResponseEntity<ResponseWrapper<List<ReleaseDto>>> getArtistReleases(ArtistResponseDto artistResponseDto){
		Integer total = artistResponseDto.getPagination().getItems();
		
		int page=1;
		int perPage=500;
		WebClient webClient = null;
		
		List<ReleaseDto> lstReleaseDtos = new ArrayList<>();
		int count=0;
		while(count<total) {
			String paginationUrl = new StringBuilder().append(artistResponseDto.getReleasesUrl()).append("?page=").append(page).append("&per_page=").append(perPage).toString();
			webClient = Webclient.getClient(paginationUrl);
			ReleaseCollectionDto releaseCollectionDto = webClient.get().exchange().block().bodyToMono(ReleaseCollectionDto.class).block();
			lstReleaseDtos.addAll(releaseCollectionDto.getReleases());
			count += releaseCollectionDto.getReleases().size();
			page++;
			paginationUrl = releaseCollectionDto.getPagination().getUrls().getNext();
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
