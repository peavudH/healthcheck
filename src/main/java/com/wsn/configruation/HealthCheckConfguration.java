package com.wsn.configruation;

import org.apache.activemq.command.ActiveMQTempTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Topic;

/**
 * Created by Lenovo on 2017/8/19.
 */
@Configuration
public class HealthCheckConfguration {
    /**
     * 定义一下名为“fireinfohealthcheck"的topic对象
     * @return
     */
    @Bean
    public Topic topic() {
        return new ActiveMQTempTopic("fireinfohealthcheck");
    }

}
