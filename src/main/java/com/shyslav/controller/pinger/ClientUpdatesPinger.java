package com.shyslav.controller.pinger;

import com.happycake.sitemodels.HappyCakeNotifications;
import com.shyslav.controller.actions.ClientActions;
import com.shyslav.defaults.ErrorCodes;
import com.shyslav.defaults.HappyCakeResponse;
import com.shyslav.models.UserUpdate;
import com.shyslav.models.UserUpdatesList;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * @author Shyshkin Vladyslav on 20.05.17.
 */
public class ClientUpdatesPinger {
    private static final Logger log = Logger.getLogger(ClientUpdatesPinger.class.getName());
    //every 10 seconds ask server about updates
    private final int delay;

    private final ClientActions client;
    private Boolean work;

    //statistic
    private long amountEmptyAnswers = 0;
    private long amountNotEmptyAnswers = 0;

    //listeners
    private final HashMap<HappyCakeNotifications, PingerListener> listenerHashMap;

    /**
     * Constructor
     *
     * @param client server client protocol
     */
    public ClientUpdatesPinger(ClientActions client) {
        this.client = client;
        this.work = true;
        this.delay = 10000;
        this.listenerHashMap = new LinkedHashMap<>();
        startPingerThread();
    }

    /**
     * Constructor
     *
     * @param client server client protocol
     */
    public ClientUpdatesPinger(ClientActions client, int delay) {
        this.client = client;
        this.work = true;
        this.delay = delay;
        this.listenerHashMap = new LinkedHashMap<>();
        startPingerThread();
    }


    /**
     * Start pinger thread
     */
    public void startPingerThread() {
        Thread thread = new Thread(() -> {
            while (work) {
                try {
                    Thread.sleep(delay);
                    HappyCakeResponse response = client.anyUpdates();
                    if (response.getCode() != ErrorCodes.EMPTY) {
                        amountNotEmptyAnswers++;
                        UserUpdatesList userUpdates = response.getObject(UserUpdatesList.class);
                        for (UserUpdate userUpdate : userUpdates) {
                            if (listenerHashMap.containsKey(userUpdate.getAction())) {
                                listenerHashMap.get(userUpdate.getAction()).onAction(userUpdate);
                            }
                        }
                    } else {
                        amountEmptyAnswers++;
                    }
                } catch (Exception e) {
                    log.error("Unable to parse pinger action " + e, e);
                }
            }
        });
        thread.setName(" Updates pinger thread ");
        thread.start();
    }

    /**
     * Off pinger thread
     */
    public void offPingerThread() {
        synchronized (work) {
            this.work = false;
        }
    }

    public boolean isWork() {
        synchronized (work) {
            return work;
        }
    }

    /**
     * Get amount empty answers
     *
     * @return amount of empty answers
     */
    public synchronized long getAmountEmptyAnswers() {
        return amountEmptyAnswers;
    }

    /**
     * Get amount not empty answers
     *
     * @return amount of not empty answers
     */
    public synchronized long getAmountNotEmptyAnswers() {
        return amountNotEmptyAnswers;
    }

    /**
     * add listener to event
     *
     * @param name  unique name of listener
     * @param event to execute
     */
    public synchronized void addListener(HappyCakeNotifications name, PingerListener event) {
        listenerHashMap.put(name, event);
    }

    public synchronized void clearListeners() {
        listenerHashMap.clear();
    }
}
