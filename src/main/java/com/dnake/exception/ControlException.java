package com.dnake.exception;

public class ControlException extends Exception {

	public ControlException() {
	}

	public ControlException(String message) {
		super(message);
	}

	public ControlException(Throwable cause) {
		super(cause);
	}

	public ControlException(String message, Throwable cause) {
		super(message, cause);
	}

}
