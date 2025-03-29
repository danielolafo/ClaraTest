package com.clara.test.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ReleaseCollectionDto {

	private PaginationDto pagination;
	private List<ReleaseDto> releases;
}
