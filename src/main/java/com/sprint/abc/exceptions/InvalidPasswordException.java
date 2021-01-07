package com.sprint.abc.exceptions;

public class InvalidPasswordException extends RuntimeException {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	public InvalidPasswordException(String message) {
		super(message);
	}
}


/**
@ExceptionHandler(InvalidPasswordException.class)
	public ResponseEntity<?> customInvalidPassword(Exception ex, WebRequest request){
		BaseErrorResponse errors = new BaseErrorResponse();
		errors.setTimestamp(LocalDateTime.now());
		errors.setMessage(ex.getMessage());
		errors.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
*/

// 