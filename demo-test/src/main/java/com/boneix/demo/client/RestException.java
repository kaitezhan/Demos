package com.boneix.demo.client;

import java.io.IOException;

public class RestException extends IOException {
    private static final long serialVersionUID = -4007999999227115865L;

    public RestException() {
    }

    public RestException(String message) {
        super(message);
    }

    public RestException(Throwable cause) {
        initCause(cause);
    }

    public RestException(String message, Throwable cause) {
        super(message);
        initCause(cause);
    }
}