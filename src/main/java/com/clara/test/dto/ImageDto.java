package com.clara.test.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ImageDto {
	public String type;
    public String uri;
    public String resource_url;
    public String uri150;
    public int width;
    public int height;

}
