package com.clara.test.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class InvalidValueException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5670821328105744701L;
	private String message;

}
