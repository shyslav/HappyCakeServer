package com.shyslav.controller.actions;

/**
 * @author Maxim Kulik
 */
public class NotStorageError extends Error {

    public NotStorageError() {
        this("Function is not use storage action");
    }

    public NotStorageError(String message) {
        super(message);
    }

    public NotStorageError(String message, Throwable cause) {
        super(message, cause);
    }
}
