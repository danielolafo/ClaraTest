package com.clara.test.dto;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
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

    @Column(nullable = false, length = 100)
    private String styleName;

    @OneToMany(mappedBy = "style")
    private Set<ReleaseStyleDto> styleReleaseStyles;
}
