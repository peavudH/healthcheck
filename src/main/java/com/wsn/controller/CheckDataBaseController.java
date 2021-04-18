package com.wsn.controller;

import com.wsn.entity.CustomResponse;
import com.wsn.service.CheckDataBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Lenovo on 2017/8/19.
 */
@RestController
public class CheckDataBaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckDataBaseController.class);

    @Autowired
    private CheckDataBaseService checkDataBaseService;

    @PostMapping("/checkdatabase")
    public CustomResponse checkDataBase(){
        CustomResponse customResponse = new CustomResponse();
        try {
            customResponse = checkDataBaseService.checkDataBase();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return customResponse;
    }
}
