package com.wsn.controller;

import com.wsn.entity.CustomResponse;
import com.wsn.service.CheckWebServerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Lenovo on 2017/8/19.
 */
@RestController
public class CheckWebServerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckWebServerController.class);

    @Autowired
    private CheckWebServerService checkWebServerService;

    @PostMapping("/checkwebserver")
    public CustomResponse checkWebServer() {
        CustomResponse customResponse = new CustomResponse();
        try {
            customResponse = checkWebServerService.requestFireinfoUrl();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return customResponse;
    }


}
