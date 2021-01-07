package com.sprint.abc.exceptions;

public class InValidDomainException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InValidDomainException() {

	}

	public InValidDomainException(String error) {
		super(error);
	}

}
