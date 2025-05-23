package com.clara.test.dto;

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
public class LabelDto {
	private Integer id;

    private String labelName;
    
    private Integer frequency;
    
    //private Set<ReleaseLabelDto> labelReleaseLabels;
}
