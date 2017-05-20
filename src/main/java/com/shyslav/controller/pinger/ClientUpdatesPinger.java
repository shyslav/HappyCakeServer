package com.shyslav.controller.pinger;

import com.shyslav.controller.ServerClient;
import com.shyslav.defaults.ErrorCodes;
import com.shyslav.defaults.HappyCakeRequest;
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

    private final ServerClient client;
    private Boolean work;

    //statistic
    private long amountEmptyAnswers = 0;
    private long amountNotEmptyAnswers = 0;

    //listeners
    private final HashMap<String, PingerListener> listenerHashMap;

    /**
     * Constructor
     *
     * @param client server client protocol
     */
    public ClientUpdatesPinger(ServerClient client) {
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
    public ClientUpdatesPinger(ServerClient client, int delay) {
        this.client = client;
        this.work = true;
        this.delay = delay;
        this.listenerHashMap = new LinkedHashMap<>();
        startPingerThread();
    }

    /**
     * Get updates for user
     *
     * @return updates
     */
    private HappyCakeResponse anyUpdates() {
        HappyCakeRequest request = new HappyCakeRequest("anyUpdates");
        return client.writeAndRead(request);
    }


    /**
     * Start pinger thread
     */
    public void startPingerThread() {
        Thread thread = new Thread(() -> {
            while (work) {
                try {
                    Thread.sleep(delay);
                    HappyCakeResponse response = anyUpdates();
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
                } catch (InterruptedException e) {
                    log.trace("Unable to sleep updates pinger thread");
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
    public long getAmountEmptyAnswers() {
        return amountEmptyAnswers;
    }

    /**
     * Get amount not empty answers
     *
     * @return amount of not empty answers
     */
    public long getAmountNotEmptyAnswers() {
        return amountNotEmptyAnswers;
    }

    /**
     * add listener to event
     *
     * @param name  unique name of listener
     * @param event to execute
     */
    public void addListener(String name, PingerListener event) {
        listenerHashMap.put(name, event);
    }
}
