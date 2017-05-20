package com.shyslav.models;

/**
 * @author Shyshkin Vladyslav on 20.05.17.
 */
public class UserUpdate {
    //url of action
    private final String action;
    private final String context;

    public UserUpdate(String action, String context) {
        this.action = action;
        this.context = context;
    }

    public String getAction() {
        return action;
    }

    public String getContext() {
        return context;
    }
}
