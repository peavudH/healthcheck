package com.wsn.controller;

import com.wsn.service.CheckActiveMQService;
import com.wsn.untils.MQProducer;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Destination;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Lenovo
 * Date: 2017/9/2
 * Time: 9:52
 * Description:
 */

@RestController
public class CheckActiveMQController {

    @Autowired
    private Environment environment;
    @Autowired
    private CheckActiveMQService checkActiveMQService;
    @Autowired
    private MQProducer mqProducer;

    @PostMapping("/checkactivemq")
    public Map<String, Object> checkActiveMQ() {
        Map<String, Object> map = new HashMap<>();
        String topicName = environment.getProperty("mq.topicName");
        String message = environment.getProperty("mq.message");
        //MQ服务器的IP地址
        String mqUrl = environment.getProperty("spring.activemq.broker-url");
        String serviceHost = mqUrl.substring(mqUrl.indexOf("//") + 2, mqUrl.lastIndexOf(":"));
        String port = mqUrl.substring(mqUrl.lastIndexOf(":") + 1,mqUrl.length());
        Destination destination = new ActiveMQTopic(topicName);
        //发送检查消息
        checkActiveMQService.sendCheckMQMessage(destination,message);
        //从消息队列获取并判断
        boolean healthResult = checkActiveMQService.judgeIsHealth(message);
        map.put("result",healthResult);//检查结果
        map.put("serviceHost", serviceHost);//ip地址
        map.put("port", port);//端口
        return map;
    }

}
