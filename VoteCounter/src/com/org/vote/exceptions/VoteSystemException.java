package com.org.vote.exceptions;

/**
 * 
 * @author JJeyaraman
 *
 */
public class VoteSystemException extends Exception {
	private static final long serialVersionUID = 7718828512143293558L;

	/**
	 * VoteSystemException constructor
	 */
	public VoteSystemException() {
		super();
	}

	/**
	 * 
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public VoteSystemException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * 
	 * @param message
	 * @param cause
	 */
	public VoteSystemException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * 
	 * @param message
	 */
	public VoteSystemException(String message) {
		super(message);
	}

	/**
	 * 
	 * @param cause
	 */
	public VoteSystemException(Throwable cause) {
		super(cause);
	}
}
