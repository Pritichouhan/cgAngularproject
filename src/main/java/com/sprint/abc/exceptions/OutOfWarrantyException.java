package com.sprint.abc.exceptions;

public class OutOfWarrantyException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public OutOfWarrantyException() {
		
	}
	
	public OutOfWarrantyException(String error) {
		super(error);
	}
	
	public String toString(){
	     return ("No Complaint inserted") ;
	}
}
