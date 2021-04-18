package com.wsn.controller;

import com.wsn.service.CheckWebServerService;
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
public class CheckWebServerController {

    @Autowired
    private Environment environment;
    @Autowired
    private SendSimpleEmail simpleEmail;

    @PostMapping("/checkwebserver")
    public Map<String, Object> checkWebServer() {
        Map<String, Object> map = new HashMap<>();
        String webServerUrl = environment.getProperty("web.requestUrl");
        String serviceHostAndPort = webServerUrl.split("/")[2];
        String serviceHost = serviceHostAndPort.split(":")[0];
        String port = serviceHostAndPort.split(":")[1];
        String subject = "WEB server exception";//异常邮件主题
        //String text = new StringBuffer(serviceHost).append("is exception").toString();//异常邮件内容
        boolean result = CheckWebServerService.requestFireinfoUrl(webServerUrl);
        if (!result) {
            simpleEmail.sendSimpleEmail(subject,serviceHost);
        }
        map.put("serviceHost", serviceHost);
        map.put("port", port);
        map.put("result", result);
        return map;
    }


}
