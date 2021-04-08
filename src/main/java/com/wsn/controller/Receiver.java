package com.wsn.controller;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * Created by Lenovo on 2017/8/19.
 */
@Component
public class Receiver {

    @JmsListener(destination = "fireinfohealthcheck")
    public boolean receiveMessage(String message) {
        if (message != null && message.equals("ActiveMQ service is health!")) {
            return true;
        }
        return false;
    }
}
