package com.clara.test.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PaginationDto {

	private int page;
	private int pages;
	private int perPpage;
	private int items;
	private UrlsDto urls;
}
