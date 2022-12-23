package com.asd.tecnical_test_asd.exeption;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final HttpStatus status;
	private final String message;
	private final Throwable cause;

	public CustomException(HttpStatus status, String message) {
		super();
		this.status = status;
		this.message = message;
		cause = null;
	}

	public CustomException(HttpStatus status, String message, Throwable cause) {
		super();
		this.status = status;
		this.message = message;
		this.cause = cause;
	}

	public HttpStatus getStatus() {
		return status;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public synchronized Throwable getCause() {
		return cause;
	}

}
