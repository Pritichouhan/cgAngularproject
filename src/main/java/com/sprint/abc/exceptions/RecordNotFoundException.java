package com.sprint.abc.exceptions;

public class RecordNotFoundException extends RuntimeException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//public static Logger log = Logger.getLogger(RecordNotFoundException.class.getName());
	/**
	 * @param msg
	 *            error message
	 */
	public RecordNotFoundException(String msg) {
		super(msg);
		//log.info("Start");

	}

}
