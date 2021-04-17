package com.wsn.controller;

import com.wsn.service.CheckWebServerService;
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

    @PostMapping("/checkwebserver")
    public Map<String, Object> checkWebServer() {
        Map<String, Object> map = new HashMap<>();
        String webServerUrl = environment.getProperty("web.requestUrl");
        String serviceHostAndPort = webServerUrl.split("/")[2];
        String serviceHost = serviceHostAndPort.split(":")[0];
        String port = serviceHostAndPort.split(":")[1];
        boolean result = CheckWebServerService.requestFireinfoUrl(webServerUrl);
        map.put("serviceHost", serviceHost);
        map.put("port", port);
        map.put("result", result);
        return map;
    }


}
