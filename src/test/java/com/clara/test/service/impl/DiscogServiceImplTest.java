package com.clara.test.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import com.clara.test.dto.ArtistComparissonRequestDto;
import com.clara.test.dto.ArtistDiscogResponseDto;
import com.clara.test.dto.ArtistReleaseDto;
import com.clara.test.dto.ArtistRequestDto;
import com.clara.test.dto.ArtistResponseDto;
import com.clara.test.dto.DiscographyRequestDto;
import com.clara.test.dto.MasterResponseDto;
import com.clara.test.dto.ReleaseDto;
import com.clara.test.dto.ResponseWrapper;
import com.clara.test.dto.ResultDto;
import com.clara.test.entity.Artist;
import com.clara.test.exception.DatabaseException;
import com.clara.test.exception.InvalidValueException;
import com.clara.test.feign.DiscogFeign;
import com.clara.test.service.ArtistReleaseService;
import com.clara.test.service.ArtistService;
import com.clara.test.service.GenreService;
import com.clara.test.service.LabelService;
import com.clara.test.service.ReleaseGenreService;
import com.clara.test.service.ReleaseLabelService;
import com.clara.test.service.ReleaseService;
import com.clara.test.service.ReleaseStyleService;
import com.clara.test.service.StyleService;

@ExtendWith(MockitoExtension.class)
class DiscogServiceImplTest {
	
	@InjectMocks
	private DiscogServiceImpl discogServiceImpl;
	
	@Mock
	private DiscogFeign discogFeign;
	
	@Mock
	private ArtistService artistService;
	
	@Mock
	private ReleaseService releaseService;
	
	@Mock
	private ArtistReleaseService artistReleaseService;
	
	@Mock
	private LabelService labelService;
	
	@Mock
	private StyleService styleService;
	
	@Mock
	private GenreService genreService;
	
	@Mock
	private ReleaseLabelService releaseLabelService;
	
	@Mock
	private ReleaseGenreService releaseGenreService;
	
	@Mock
	private ReleaseStyleService releaseStyleService;
	
	ResponseEntity<ArtistDiscogResponseDto> discogResp;
	ResponseEntity<MasterResponseDto> masterResponse;
	ResponseEntity<ArtistResponseDto> artistResponseDto;
	ResponseEntity<ResponseWrapper<ArtistResponseDto>> artistServiceResp;
	ResponseEntity<ResponseWrapper<List<ReleaseDto>>> discographyResp;

	@BeforeEach
	void setUp() throws Exception {
		List<ResultDto> lstResults = new ArrayList<>();
		lstResults.add(ResultDto.builder().build());
		discogResp = new ResponseEntity<>(
				ArtistDiscogResponseDto.builder()
				.results(lstResults)
				.build(),
				HttpStatus.OK);
		
		List<Artist> lstArtist = new ArrayList<>();
		lstArtist.add(Artist.builder().name("Artist 1").id(1L).build());
		masterResponse = new ResponseEntity<>(
				MasterResponseDto.builder()
				.artists(lstArtist)
				.build(),
				HttpStatus.OK);
		
		List<ArtistReleaseDto> lstArtistReleases = new ArrayList<>();
		artistResponseDto = new ResponseEntity<>(
				ArtistResponseDto.builder()
				.artistReleases(lstArtistReleases)
				.releasesUrl("http://myrul.com")
				.build(),
				HttpStatus.OK);
		
		artistServiceResp = new ResponseEntity<>(
				ResponseWrapper.<ArtistResponseDto>builder()
				.data(ArtistResponseDto.builder().id(1L).build())
				.build(),
				HttpStatus.OK);
		
		List<ReleaseDto> lstReleaseDtos = new ArrayList<>();
		lstReleaseDtos.add(ReleaseDto.builder().artistId(1).title("Release 1").build());
		discographyResp = new ResponseEntity<>(
				ResponseWrapper.<List<ReleaseDto>>builder()
				.data(lstReleaseDtos)
				.build(),
				HttpStatus.OK);
		
		ReflectionTestUtils.setField(discogServiceImpl, "discogToken", "discogToken");
	}

	@Test
	void getArtist() throws InvalidValueException, DatabaseException {
		doReturn(masterResponse).when(discogFeign).getMaster(anyInt());
		doReturn(discogResp).when(discogFeign).getArtist(anyString(), anyString(), anyString(), anyString());
		doReturn(artistResponseDto).when(discogFeign).getArtistById(anyLong());
		doReturn(artistResponseDto).when(artistService).findByName(ArtistResponseDto.builder().name("Artist1").build());
		//doReturn(artistResponseDto).when(discogFeign).get(any(ArtistResponseDto.class));
		discogServiceImpl.getArtist(ArtistRequestDto.builder().artist("Artist1").title("Title").releaseTitle("Release title").build());
	}
	
	@Test
	void compareArtists() {
		doReturn(masterResponse).when(discogFeign).getMaster(anyInt());
		doReturn(discogResp).when(discogFeign).getArtist(anyString(), anyString(), anyString(), anyString());
		doReturn(artistResponseDto).when(discogFeign).getArtistById(anyLong());
		
		List<String> lstArtists = new ArrayList<>();
		lstArtists.add("U2");
		lstArtists.add("The Beatles");
		doReturn(artistResponseDto).when(artistService).findByName(ArtistResponseDto.builder().name("U2").build());
		doReturn(artistResponseDto).when(artistService).findByName(ArtistResponseDto.builder().name("The Beatles").build());
		discogServiceImpl.compareArtists(ArtistComparissonRequestDto.builder().artists(lstArtists).build());
	}
	
	@Test
	void getDiscography() {
		when(artistService.findById(anyInt())).thenReturn(artistServiceResp);
		when(releaseService.getDiscography(any(DiscographyRequestDto.class))).thenReturn(discographyResp);
		DiscographyRequestDto discographyRequestDto = DiscographyRequestDto.builder()
				.page(1).pageSize(10)
				.artistId(1)
				.build();
		discogServiceImpl.getDiscography(discographyRequestDto);
	}

}
