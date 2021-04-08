package com.wsn.entity;

import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * Created by Lenovo on 2017/8/19.
 */
public class CheckActiveMQMessage implements MessageCreator {
    @Override
    public Message createMessage(Session session) throws JMSException {
        return session.createTextMessage("ActiveMQ service is health!");
    }
}
