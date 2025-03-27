package com.clara.test.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ArtistDiscogResponseDto {

	private PaginationDto pagination;
    private List<ResultDto> results;
}
