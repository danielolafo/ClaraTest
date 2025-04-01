package com.clara.test.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DatabaseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8900393789401673518L;
	private String message;
}
