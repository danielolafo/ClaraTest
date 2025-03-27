package com.clara.test.dto;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ResponseWrapper<T> {

	private T data;
	private HttpStatus status;
	private String message;
	
}
