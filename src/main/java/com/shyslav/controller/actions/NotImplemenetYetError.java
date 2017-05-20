package com.shyslav.controller.actions;

/**
 * @author Maxim Kulik
 */
public class NotImplemenetYetError extends Error {

    public NotImplemenetYetError() {
        this("Function is not implemented yet");
    }

    public NotImplemenetYetError(String message) {
        super(message);
    }

    public NotImplemenetYetError(String message, Throwable cause) {
        super(message, cause);
    }
}
