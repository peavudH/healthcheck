package com.wsn.controller;

import com.wsn.service.CheckDataBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public boolean checkDataBase(){
        return checkDataBaseService.checkDataBase(environment.getProperty("mysql.sql"),environment.getProperty
                ("mysql.expectResult"));
    }
}
