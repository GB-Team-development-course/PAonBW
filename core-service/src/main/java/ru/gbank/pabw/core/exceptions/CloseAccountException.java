package ru.gbank.pabw.core.exceptions;

public class CloseAccountException extends RuntimeException{

    public  CloseAccountException() {
        super();
    }

    public  CloseAccountException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public  CloseAccountException(final String message) {
        super(message);
    }

    public  CloseAccountException(final Throwable cause) {
        super(cause);
    }
}
