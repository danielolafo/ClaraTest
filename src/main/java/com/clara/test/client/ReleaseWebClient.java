package com.clara.test.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

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
		//Integer total = artistResponseDto.getPagination().getItems();
		
		int page=1;
		int perPage=500;
		WebClient webClient = null;
		
		List<ReleaseDto> lstReleaseDtos = new ArrayList<>();
		int count=0;
		String paginationUrl = "";
		paginationUrl = new StringBuilder().append(artistResponseDto.getReleasesUrl()).append("?page=").append(page).append("&per_page=").append(perPage).toString();
		webClient = Webclient.getClient(paginationUrl);
		ReleaseCollectionDto releaseCollectionDto = webClient.get().exchange().block().bodyToMono(ReleaseCollectionDto.class).block();
		//paginationUrl = null;
		if(Objects.nonNull(releaseCollectionDto) && Objects.nonNull(releaseCollectionDto.getReleases())) {
			lstReleaseDtos.addAll(releaseCollectionDto.getReleases());
			count += releaseCollectionDto.getReleases().size();
			page++;
			paginationUrl = releaseCollectionDto.getPagination().getUrls().getNext();
		}
		
		//ReleaseWebClient.saveAllReleases(artistResponseDto);
		
		return new ResponseEntity<>(
				ResponseWrapper.<List<ReleaseDto>>builder()
				.data(lstReleaseDtos)
				.build(),
				HttpStatus.OK);
	}
	
	public static CompletableFuture<Void> saveAllReleases(ArtistResponseDto artistResponseDto) {
		int page=2;
		int perPage=500;
		WebClient webClient = null;
		
		List<ReleaseDto> lstReleaseDtos = new ArrayList<>();
		int count=0;
		String paginationUrl = "";
		while(Objects.nonNull(paginationUrl) || count<100) {
			paginationUrl = new StringBuilder().append(artistResponseDto.getReleasesUrl()).append("?page=").append(page).append("&per_page=").append(perPage).toString();
			webClient = Webclient.getClient(paginationUrl);
			ReleaseCollectionDto releaseCollectionDto = webClient.get().exchange().block().bodyToMono(ReleaseCollectionDto.class).block();
			paginationUrl = null;
			if(Objects.nonNull(releaseCollectionDto) && Objects.nonNull(releaseCollectionDto.getReleases())) {
				lstReleaseDtos.addAll(releaseCollectionDto.getReleases());
				count += releaseCollectionDto.getReleases().size();
				page++;
				paginationUrl = releaseCollectionDto.getPagination().getUrls().getNext();
			}
			
		}
		return null;
		
	}

}
