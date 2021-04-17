package com.wsn.controller;

import com.wsn.service.CheckFireVgwService;
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

    @PostMapping("/checkfirevgw")
    public Map<String,Object> checkFireVgw() {
        Map<String, Object> map = new HashMap<>();
        String serviceHost = environment.getProperty("firevgw.serviceHost");
        Integer port = environment.getProperty("firevgw.port", Integer.class);
        String message = environment.getProperty("firevgw.message");
        boolean result = checkFireVgwService.checkFireVgw(serviceHost, port, message);
        map.put("serviceHost", serviceHost);
        map.put("port", port);
        map.put("result", result);
        return map;
    }
}
