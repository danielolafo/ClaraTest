package com.clara.test.service.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.clara.test.dto.CommunityDto;
import com.clara.test.dto.ResponseWrapper;
import com.clara.test.entity.Community;
import com.clara.test.mapper.CommunityMapper;
import com.clara.test.repository.CommunityRepository;
import com.clara.test.service.CommunityService;

import lombok.NonNull;

@Service
public class CommunityServiceImpl implements CommunityService {

	@NonNull
	private CommunityRepository repository;
	
	public CommunityServiceImpl(CommunityRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public ResponseEntity<ResponseWrapper<CommunityDto>> findByReleaseIdAndWantAndHave() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<ResponseWrapper<List<CommunityDto>>> save(List<CommunityDto> lstCommunityDtos) {
		for(CommunityDto communityDto : lstCommunityDtos) {
			Community savedCommunity = this.repository.save(CommunityMapper.INSTANCE.toEntity(communityDto));
			communityDto.setId(savedCommunity.getId());
		}
		return new ResponseEntity<>(
				ResponseWrapper.<List<CommunityDto>>builder()
				.data(lstCommunityDtos)
				.message(!lstCommunityDtos.isEmpty() ? HttpStatus.OK.getReasonPhrase() : HttpStatus.NOT_FOUND.getReasonPhrase())
				.build(),
				!lstCommunityDtos.isEmpty() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
	}

}
