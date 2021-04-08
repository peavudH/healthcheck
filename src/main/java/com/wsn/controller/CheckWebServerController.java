package com.wsn.controller;

import com.wsn.service.CheckWebServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Lenovo on 2017/8/19.
 */
@RestController
public class CheckWebServerController {

    @Autowired
    private Environment environment;

    @PostMapping("/checkwebserver")
    public boolean checkWebServer() {
        return CheckWebServerService.requestFireinfoUrl(environment.getProperty("web.requestUrl"));
    }


}
