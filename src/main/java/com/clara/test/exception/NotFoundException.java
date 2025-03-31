package com.clara.test.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NotFoundException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7505291809986929224L;
	private String message;

}
