package com.wsn.untils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Destination;

/**
 * Created with IntelliJ IDEA.
 * User: Lenovo
 * Date: 2017/9/2
 * Time: 9:04
 * Description:
 */

@Component
public class MQProducer {

    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendMessage(Destination destination, String message) {
        jmsTemplate.convertAndSend(destination, message);
    }
}
