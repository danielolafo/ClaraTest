package com.clara.test.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ArtistDiscogResponseDto {

	private PaginationDto pagination;
    private List<ResultDto> results;
}
