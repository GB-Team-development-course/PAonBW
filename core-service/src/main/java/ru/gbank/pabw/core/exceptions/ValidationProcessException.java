package ru.gbank.pabw.core.exceptions;

import java.util.List;

public class ValidationProcessException extends RuntimeException{

    public ValidationProcessException() {
        super();
    }

    public ValidationProcessException(final List<String> message, final Throwable cause) {
        super(message.toString(), cause);
    }

    public ValidationProcessException(final List<String> message) {
        super(message.toString());
    }
}
