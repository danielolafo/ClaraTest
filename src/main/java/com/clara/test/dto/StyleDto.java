package com.clara.test.dto;

import java.util.Set;

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
public class StyleDto {
	private Integer id;

    private String styleName;

    private Set<ReleaseStyleDto> styleReleaseStyles;
    
    private Integer frequency;
}
