package com.shyslav.models;

import com.happycake.sitemodels.HappyCakeNotifications;

/**
 * @author Shyshkin Vladyslav on 20.05.17.
 */
public class UserUpdate {
    //url of action
    private final HappyCakeNotifications action;
    private final String context;

    public UserUpdate(HappyCakeNotifications action, String context) {
        this.action = action;
        this.context = context;
    }

    public HappyCakeNotifications getAction() {
        return action;
    }

    public String getContext() {
        return context;
    }
}
