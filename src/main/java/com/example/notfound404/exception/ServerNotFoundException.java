package com.example.notfound404.exception;

public class ServerNotFoundException extends RuntimeException {
    public ServerNotFoundException() {
        super();
    }

    public ServerNotFoundException(String message) {
        super(message);
    }

    public ServerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServerNotFoundException(Throwable cause) {
        super(cause);
    }
}
