package com.example.notfound404.exception;

public class InvalidServerException extends RuntimeException {

    public InvalidServerException() {
        super();
    }

    public InvalidServerException(String message) {
        super(message);
    }

    public InvalidServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidServerException(Throwable cause) {
        super(cause);
    }
}

