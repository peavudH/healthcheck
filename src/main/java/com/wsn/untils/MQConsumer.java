package com.wsn.untils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

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
    private String actuals;

    private static final Logger LOGGER = LoggerFactory.getLogger(MQConsumer.class);
    @JmsListener(destination = "fireinfohealthcheck")
    public void receiveTopic(String message) {
        LOGGER.info("从消息队列获取到的消息："+message);
        actuals = message;
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
        return false;
    }
}
