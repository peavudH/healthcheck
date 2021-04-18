package com.wsn.service;

import com.wsn.entity.CustomResponse;
import com.wsn.untils.MQConsumer;
import com.wsn.untils.MQProducer;
import com.wsn.untils.SendSimpleEmail;
import org.apache.activemq.command.ActiveMQTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.jms.Destination;

/**
 * Created with IntelliJ IDEA.
 * User: Lenovo
 * Date: 2017/9/2
 * Time: 9:49
 * Description:
 */
@Service
public class CheckActiveMQService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckActiveMQService.class);

    @Autowired
    private MQProducer mqProducer;
    @Autowired
    private MQConsumer mqConsumer;
    @Autowired
    private Environment environment;
    @Autowired
    private SendSimpleEmail simpleEmail;

    /**
     * 发送检查消息
     * @param destination
     * @param message
     */
    public boolean sendCheckMQMessage(Destination destination, String message) {
        try {
            mqProducer.sendMessage(destination, message);
            return true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }

    /**
     * 根据返回的获取的消息判断
     * @return
     */
    public CustomResponse judgeIsHealth() throws Exception{
        CustomResponse customResponse = new CustomResponse();
        boolean result = false;
        try {
            String topicName = environment.getProperty("mq.topicName");
            String message = environment.getProperty("mq.message");
            //MQ服务器的IP地址
            String mqUrl = environment.getProperty("spring.activemq.broker-url");
            String serviceHost = mqUrl.substring(mqUrl.indexOf("//") + 2, mqUrl.lastIndexOf(":"));
            String port = mqUrl.substring(mqUrl.lastIndexOf(":") + 1, mqUrl.length());
            String subject = "ActiveMQ server exception";//异常邮件主题
            String text = new StringBuffer(serviceHost).append(" is exception").toString();//异常邮件内容
            Destination destination = new ActiveMQTopic(topicName);
            //发送检查消息
            result = this.sendCheckMQMessage(destination, message);
            //如果发送消息出现异常，则直接发送异常邮件
            if (!result) {
                LOGGER.warn("MQ server is exception,Is now ready to send a notification message!");
                simpleEmail.sendSimpleEmail(subject, text);
            }
            //当发送成功后，再判断是否能正常接收
            else {
                //从消息队列获取并判断
                result = mqConsumer.judgeIsHealth(message);
                if (!result) {
                    LOGGER.warn("MQ server is exception,Is now ready to send a notification message!");
                    simpleEmail.sendSimpleEmail(subject, text);
                }
            }
            customResponse.setServerName("MQ");
            customResponse.setPort(port);
            customResponse.setServiceHost(serviceHost);
            customResponse.setResult(result);
        } catch (Exception e) {
            LOGGER.error("Verify that the MQ server has an exception");
            throw e;
        }
        return customResponse;
    }

}
