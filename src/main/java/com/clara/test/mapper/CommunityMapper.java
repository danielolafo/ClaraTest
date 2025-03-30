package com.clara.test.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.clara.test.dto.CommunityDto;
import com.clara.test.entity.Community;

@Mapper
public interface CommunityMapper {
	
	CommunityMapper INSTANCE = Mappers.getMapper(CommunityMapper.class);
	
	public CommunityDto toDto(Community community);
	
	public Community toEntity(CommunityDto communityDto);

}
