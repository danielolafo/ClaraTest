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
public class ReleaseLabelDto {
	private Integer id;

  
    private ReleaseDto release;

    
    private LabelDto label;
}
