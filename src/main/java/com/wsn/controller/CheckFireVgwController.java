package com.wsn.controller;

import com.wsn.service.CheckFireVgwService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public boolean checkFireVgw() {
        return checkFireVgwService.checkFireVgw(environment.getProperty("firevgw.serviceHost"), environment
                .getProperty("firevgw.port", Integer.class), environment.getProperty("firevgw.message"));
    }
}
