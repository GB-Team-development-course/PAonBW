package ru.gb.core.exceptions;

public class ValidationProcessException extends RuntimeException{

    private static final long serialVersionUID = 5861310537366287164L; //?

    public ValidationProcessException() {
        super();
    }

    public ValidationProcessException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ValidationProcessException(final String message) {
        super(message);
    }

    public ValidationProcessException(final Throwable cause) {
        super(cause);
    }
}
