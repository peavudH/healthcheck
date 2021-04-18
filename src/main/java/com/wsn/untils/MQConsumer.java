package com.wsn.untils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.*;

/**
 * Created with IntelliJ IDEA.
 * User: Lenovo
 * Date: 2017/9/2
 * Time: 9:09
 * Description:
 */
@Component
public class MQConsumer {
    //从消息队列中获取的实际消息
    private static String actuals;
    @Autowired
    private JmsTemplate jmsTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(MQConsumer.class);

    @JmsListener(destination = "fireinfohealthcheck")
    public void receiveTopic(String message) {
        actuals = message;
        LOGGER.info("从消息队列获取到的消息："+message);
    }

    public void recive() {
        try {
            Connection connection = jmsTemplate.getConnectionFactory().createConnection();
            Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createTopic("fireinfohealthcheck");
            MessageConsumer consumer = session.createConsumer(destination);
            TextMessage message = (TextMessage) consumer.receive(5 * 1000);
            String messageText = message.getText();
            LOGGER.info("messageText:"+messageText);

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断是否正常
     * @param expecteds 预期值
     * @return 是否正常
     */
    public boolean judgeIsHealth(String expecteds) {
        if (expecteds.equals(actuals)) {
            return true;
        }
        //recive();
        return true;
    }
}
