package com.wsn.controller;

import com.wsn.service.CheckFireVgwService;
import com.wsn.untils.SendSimpleEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lenovo on 2017/8/19.
 */
@RestController
public class CheckFireVgwController {
    @Autowired
    private CheckFireVgwService checkFireVgwService;
    @Autowired
    private Environment environment;
    @Autowired
    private SendSimpleEmail simpleEmail;

    @PostMapping("/checkfirevgw")
    public Map<String,Object> checkFireVgw() {
        Map<String, Object> map = new HashMap<>();
        String serviceHost = environment.getProperty("firevgw.serviceHost");
        Integer port = environment.getProperty("firevgw.port", Integer.class);
        String message = environment.getProperty("firevgw.message");
        String subject = "vgw server exception";//异常邮件主题
        //String text = new StringBuffer(serviceHost).append(" is exception").toString();//异常邮件内容
        boolean result = checkFireVgwService.checkFireVgw(serviceHost, port, message);
        if (!result) {
            simpleEmail.sendSimpleEmail(subject,serviceHost);
        }
        map.put("serviceHost", serviceHost);
        map.put("port", port);
        map.put("result", result);
        return map;
    }
}
