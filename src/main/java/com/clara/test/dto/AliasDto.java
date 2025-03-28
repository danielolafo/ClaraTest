package com.clara.test.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AliasDto {

	private Integer id;

    private String aliasName;

    private String resourceUrl;

    private Integer artistId;
}
