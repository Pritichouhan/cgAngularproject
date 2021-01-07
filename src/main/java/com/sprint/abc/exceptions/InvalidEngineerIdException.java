package com.sprint.abc.exceptions;

public class InvalidEngineerIdException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InvalidEngineerIdException() {
		
	}
	
	public InvalidEngineerIdException(String error) {
		super(error);
	}
	

	public String toString(){
	     return ("No Engineer record found") ;
	}
}