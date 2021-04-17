package com.wsn.service;

import com.wsn.untils.MQConsumer;
import com.wsn.untils.MQProducer;
import org.springframework.beans.factory.annotation.Autowired;
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


    @Autowired
    private MQProducer mqProducer;
    @Autowired
    private MQConsumer mqConsumer;

    /**
     * 发送检查消息
     * @param destination
     * @param message
     */
    public void sendCheckMQMessage(Destination destination, String message) {
        mqProducer.sendMessage(destination,message);
    }

    /**
     * 根据返回的获取的消息判断
     * @param expecteds
     * @return
     */
    public boolean judgeIsHealth(String expecteds) {
        return mqConsumer.judgeIsHealth(expecteds);
    }

}
