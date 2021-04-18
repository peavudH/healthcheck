package com.wsn.controller;

import com.wsn.entity.CustomResponse;
import com.wsn.service.CheckFireVgwService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Lenovo on 2017/8/19.
 */
@RestController
public class CheckFireVgwController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckFireVgwController.class);
    @Autowired
    private CheckFireVgwService checkFireVgwService;


    @PostMapping("/checkfirevgw")
    public CustomResponse checkFireVgw() {
        CustomResponse customResponse = new CustomResponse();
        try {
            customResponse = checkFireVgwService.judgeVgw();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return customResponse;
    }
}
