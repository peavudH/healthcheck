package com.wsn.service;

import com.wsn.entity.CheckActiveMQMessage;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.*;
import java.util.Map;

/**
 * Created by Lenovo on 2017/8/19.
 */
@Service
public class CheckActiveMQService implements CommandLineRunner{
    @Autowired
    private JmsTemplate jmsTemplate;


    @Override
    public void run(String... strings) throws Exception {
        jmsTemplate.send("fireinfohealthcheck",new CheckActiveMQMessage());
    }

    @JmsListener(destination = "fireinfohealthcheck")
    public boolean receiveMessage(String message) {
        if (message != null && message.equals("ActiveMQ service is health!")) {
            return true;
        }
        return false;
    }
}
