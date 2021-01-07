package com.sprint.abc.exceptions;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.sprint.abc.payload.BaseErrorResponse;
import com.sprint.abc.payload.ValidationErrorResponse;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	private static Logger logger=LoggerFactory.getLogger(CustomGlobalExceptionHandler.class);

	private final String BAD_REQUEST = "BAD_REQUEST";
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> customHandleNotFound(Exception ex, WebRequest request) {
		
		BaseErrorResponse errors = new BaseErrorResponse();
		errors.setTimestamp(LocalDateTime.now());
		errors.setMessage(ex.getMessage());
		errors.setStatusCode(HttpStatus.NOT_FOUND.value());
		logger.error("Exception :: "+ex.getMessage());
		return new ResponseEntity<>(errors,HttpStatus.NOT_FOUND);

	}	
	
	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<?> customHandleProductNotFound(Exception ex, WebRequest request) {
		
		BaseErrorResponse errors = new BaseErrorResponse();
		errors.setTimestamp(LocalDateTime.now());
		errors.setMessage(ex.getMessage());
		errors.setStatusCode(HttpStatus.NOT_FOUND.value());
		logger.error("Exception :: "+ex.getMessage());
		return new ResponseEntity<>(errors,HttpStatus.NOT_FOUND);

	}	
	
	@ExceptionHandler(RecordNotFoundException.class)
	public ResponseEntity<?> customRecordNotFound(Exception ex, WebRequest request) {
		
		BaseErrorResponse errors = new BaseErrorResponse();
		errors.setTimestamp(LocalDateTime.now());
		errors.setMessage(ex.getMessage());
		errors.setStatusCode(HttpStatus.NOT_FOUND.value());
		logger.error("Exception :: "+ex.getMessage());
		return new ResponseEntity<>(errors,HttpStatus.NOT_FOUND);

	}	
	
	@ExceptionHandler(InvalidPasswordException.class)
	public ResponseEntity<?> customInvalidPassword(Exception ex, WebRequest request){
		BaseErrorResponse errors = new BaseErrorResponse();
		errors.setTimestamp(LocalDateTime.now());
		errors.setMessage(ex.getMessage());
		errors.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		logger.error("Exception :: "+ex.getMessage());
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public final ResponseEntity<ValidationErrorResponse> handleConstraintViolation(ConstraintViolationException ex,
			WebRequest request) {
		List<String> details = ex.getConstraintViolations().parallelStream().map(e -> e.getMessage())
				.collect(Collectors.toList());

		ValidationErrorResponse validationErrResp = new ValidationErrorResponse();		
		validationErrResp.setTimestamp(LocalDateTime.now());
		validationErrResp.setMessage(BAD_REQUEST);
		validationErrResp.setStatusCode(HttpStatus.BAD_REQUEST.value());
		validationErrResp.setDetails(details);
		logger.error("Exception :: "+ex.getMessage());
		return new ResponseEntity<>(validationErrResp, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(OperationFailedException.class)
	public final ResponseEntity<?> handleOperationFailedViolation(OperationFailedException ex,
			WebRequest request) {
		
		BaseErrorResponse errors = new BaseErrorResponse();
		errors.setTimestamp(LocalDateTime.now());
		errors.setMessage(ex.getMessage());
		errors.setStatusCode(HttpStatus.NOT_FOUND.value());
		logger.error("Exception :: "+ex.getMessage());
		return new ResponseEntity<>("Operation failed.", HttpStatus.NOT_FOUND);
	
	}
	
	@ExceptionHandler(OutOfWarrantyException.class)
	public ResponseEntity<?> customOutOfWarranty(Exception ex, WebRequest request){
		BaseErrorResponse errors = new BaseErrorResponse();
		errors.setTimestamp(LocalDateTime.now());
		errors.setMessage(ex.getMessage());
		errors.setStatusCode(HttpStatus.NOT_FOUND.value());
		logger.error("Exception :: "+ex.getMessage());
		return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InValidComplaintIdException.class)
	public ResponseEntity<?> customInvalidComplaintId(Exception ex, WebRequest request){
		BaseErrorResponse errors = new BaseErrorResponse();
		errors.setTimestamp(LocalDateTime.now());
		errors.setMessage(ex.getMessage());
		errors.setStatusCode(HttpStatus.NOT_FOUND.value());
		logger.error("Exception :: "+ex.getMessage());
		return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InvalidClientIdException.class)
	public ResponseEntity<?> customInvalidClientId(Exception ex, WebRequest request){
		BaseErrorResponse errors = new BaseErrorResponse();
		errors.setTimestamp(LocalDateTime.now());
		errors.setMessage(ex.getMessage());
		errors.setStatusCode(HttpStatus.NOT_FOUND.value());
		logger.error("Exception :: "+ex.getMessage());
		return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> customHandleForServerError(Exception ex, WebRequest request) {

		BaseErrorResponse errors = new BaseErrorResponse();
		errors.setTimestamp(LocalDateTime.now());
		errors.setMessage(ex.getMessage());
		errors.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		logger.error("Exception :: "+ex.getMessage());
		return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@ExceptionHandler(InvalidEngineerIdException.class)
	public ResponseEntity<?> customHandleEngineerNotFound(Exception ex, WebRequest request) {
		BaseErrorResponse errors = new BaseErrorResponse();
		errors.setTimestamp(LocalDateTime.now());
		errors.setMessage(ex.getMessage());
		errors.setStatusCode(HttpStatus.NOT_FOUND.value());
		logger.error("Exception :: "+ex.getMessage());
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);

	}	
	
	@ExceptionHandler(InValidDomainException.class)
	public ResponseEntity<?> customHandleNotexisting(Exception ex, WebRequest request) {
		BaseErrorResponse errors = new BaseErrorResponse();
		errors.setTimestamp(LocalDateTime.now());
		errors.setMessage(ex.getMessage());
		errors.setStatusCode(HttpStatus.NOT_FOUND.value());
		logger.error("Exception :: "+ex.getMessage());
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);

	}
		
	@ExceptionHandler(InvalidAdminId.class)
	public ResponseEntity<?> customHandleNotThere(Exception ex, WebRequest request) {
		BaseErrorResponse errors = new BaseErrorResponse();
		errors.setTimestamp(LocalDateTime.now());
		errors.setMessage(ex.getMessage());
		errors.setStatusCode(HttpStatus.NOT_FOUND.value());
		logger.error("Exception :: "+ex.getMessage());
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}	
	
	 @Override
	    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
	                                                                  HttpHeaders headers,
	                                                                  HttpStatus status, WebRequest request) {

	        Map<String, Object> body = new LinkedHashMap<>();
	        body.put("timestamp", new Date());
	        body.put("status", status.value());

	        //Get all errors
	        List<String> errors = ex.getBindingResult()
	                .getFieldErrors()
	                .stream()
	                .map(x -> x.getDefaultMessage())
	                .collect(Collectors.toList());

	        body.put("errors", errors);
//	        logger.error("Exception :: "+ex.getMessage());
	        return new ResponseEntity<>(body, headers, status);

	    }
	
	

}
