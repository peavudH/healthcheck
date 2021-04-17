package com.wsn.untils;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.jms.Destination;

/**
 * Created with IntelliJ IDEA.
 * User: Lenovo
 * Date: 2017/9/2
 * Time: 9:17
 * Description:
 */
@Component
public class ScheduledProducer {

    @Autowired
    private MQProducer mqProducer;
    @Autowired
    private Environment environment;

   // @Scheduled(fixedRate = 5000)
    public void sendMessage() {
        Destination destination = new ActiveMQTopic(environment.getProperty("mq.topicName"));
        mqProducer.sendMessage(destination,environment.getProperty("mq.message"));
    }
}
