package com.sprint.abc.exceptions;

public class InValidComplaintIdException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InValidComplaintIdException() {
		
	}

	public InValidComplaintIdException(String error) {
		super(error);
	}
}
