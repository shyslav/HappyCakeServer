package com.shyslav.defaults;

import com.shyslav.utils.LazyGson;

/**
 * @author Shyshkin Vladyslav on 30.04.17.
 */
public class HappyCakeResponse {
    private final ErrorCodes code;
    private final String messageText;
    private final String context;

    public HappyCakeResponse(ErrorCodes msg, String message, Object object) {
        this.code = msg;
        this.messageText = message;
        this.context = LazyGson.toJson(object);
    }

    public HappyCakeResponse(ErrorCodes msg, Object object) {
        this.code = msg;
        this.messageText = "";
        this.context = LazyGson.toJson(object);
    }

    public HappyCakeResponse(ErrorCodes code, String messageText) {
        this.code = code;
        this.messageText = messageText;
        this.context = null;
    }

    /**
     * Get object from json
     *
     * @param clazz class
     * @param <T>   class type
     * @return object from json
     */
    public <T> T getObject(Class clazz) {
        return LazyGson.fromJson(context, clazz);
    }

    public ErrorCodes getCode() {
        return code;
    }

    public String getMessageText() {
        return messageText;
    }

    public String getContext() {
        return context;
    }
}
