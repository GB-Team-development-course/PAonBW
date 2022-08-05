package ru.gbank.pawb.credit.exceptions;

public class CoreServiceException extends RuntimeException {

	private static final long serialVersionUID = 5861310537366287163L;

	public CoreServiceException() {
		super();
	}

	public CoreServiceException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public CoreServiceException(final String message) {
		super(message);
	}

	public CoreServiceException(final Throwable cause) {
		super(cause);
	}
}
