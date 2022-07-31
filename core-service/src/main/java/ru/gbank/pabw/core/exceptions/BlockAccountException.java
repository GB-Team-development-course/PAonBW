package ru.gbank.pabw.core.exceptions;

public class BlockAccountException extends RuntimeException{

    public BlockAccountException() {
        super();
    }

    public BlockAccountException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public BlockAccountException(final String message) {
        super(message);
    }

    public BlockAccountException(final Throwable cause) {
        super(cause);
    }
}
