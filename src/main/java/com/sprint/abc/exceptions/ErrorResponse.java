package com.sprint.abc.exceptions;

import java.util.Date;

public class ErrorResponse {
	
	private Date timestamp;
	private String message;
	private String details;
	
	public ErrorResponse(Date timestamp, String message, String details) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
	}
	
	

}
