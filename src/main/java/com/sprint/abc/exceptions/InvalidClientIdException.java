package com.sprint.abc.exceptions;

public class InvalidClientIdException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InvalidClientIdException() {
		
	}

	public InvalidClientIdException(String error) {
		super(error);
	}
}
