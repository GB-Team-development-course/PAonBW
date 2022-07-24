package ru.gb.core.exceptions;

public class FailedCreateClientException extends RuntimeException{

	private static final long serialVersionUID = 5861310537366287163L;

	public FailedCreateClientException() {
		super();
	}

	public FailedCreateClientException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public FailedCreateClientException(final String message) {
		super(message);
	}

	public FailedCreateClientException(final Throwable cause) {
		super(cause);
	}
}
