package com.shyslav.controller.pinger;

import com.shyslav.models.UserUpdate;

/**
 * @author Shyshkin Vladyslav on 20.05.17.
 */
public interface PingerListener {
    void onAction(UserUpdate update);
}
