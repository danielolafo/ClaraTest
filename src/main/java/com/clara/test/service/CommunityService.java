package com.clara.test.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.clara.test.dto.CommunityDto;
import com.clara.test.dto.ResponseWrapper;

public interface CommunityService {
	
	public ResponseEntity<ResponseWrapper<CommunityDto>> findByReleaseIdAndWantAndHave();
	
	public ResponseEntity<ResponseWrapper<List<CommunityDto>>> save(List<CommunityDto> lstCommunityDtos);

}
