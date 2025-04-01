package com.clara.test.utils;

import java.util.Objects;

import com.clara.test.exception.NotFoundException;
import com.fasterxml.jackson.databind.util.EnumValues;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CriteriaEnum {

	RELEASES("Release"), 
	RELEASE_YEAR("ReleaseYear"), 
	GENRES("Genre"), 
	TAGS("Tag"), 
	STYLES("Style");
	
	private String name;
	
	public static CriteriaEnum getEnum(String value) throws NotFoundException {
		CriteriaEnum[] enums = CriteriaEnum.values();
		CriteriaEnum resp = null;
		
		int idx=0;
		while(resp==null || idx<enums.length) {
			if(enums[idx].getName().equals(value)) {
				resp = enums[idx];
			}
			idx++;
		}
		if(Objects.isNull(resp)) {
			throw new NotFoundException("Criteria not found");
		}
		
		return resp;
	}
}
