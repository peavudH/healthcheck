package com.wsn.controller;

import com.wsn.service.CheckDataBaseService;
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
public class CheckDataBaseController {

    @Autowired
    private CheckDataBaseService checkDataBaseService;
    @Autowired
    private Environment environment;


    @PostMapping("/checkdatabase")
    public Map<String, Object> checkDataBase(){
        Map<String, Object> map = new HashMap<>();
        String jdbcUrl = environment.getProperty("spring.datasource.url");
        String serviceHostAndPort = jdbcUrl.substring(jdbcUrl.indexOf("//") + 2, jdbcUrl.lastIndexOf("/"));
        String serviceHost = serviceHostAndPort.split(":")[0];
        String port = serviceHostAndPort.split(":")[1];
        boolean result = checkDataBaseService.checkDataBase(environment.getProperty("mysql.sql"), environment.getProperty
                ("mysql.expectResult"));
        map.put("serviceHost", serviceHost);
        map.put("port", port);
        map.put("result", result);
        return map;
    }
}
