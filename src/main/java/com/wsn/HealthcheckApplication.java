package com.wsn;

import org.apache.activemq.command.ActiveMQTempTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.jms.Topic;

/**
 * 主入口
 */
@SpringBootApplication
@EnableScheduling
public class HealthcheckApplication {


	public static void main(String[] args) {
		SpringApplication.run(HealthcheckApplication.class, args);
	}
}
