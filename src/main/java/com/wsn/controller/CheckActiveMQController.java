package com.wsn.controller;

import com.wsn.entity.CustomResponse;
import com.wsn.service.CheckActiveMQService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 * User: Lenovo
 * Date: 2017/9/2
 * Time: 9:52
 * Description:
 */

@RestController
public class CheckActiveMQController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckActiveMQController.class);
    @Autowired
    private CheckActiveMQService checkActiveMQService;


    @PostMapping("/checkactivemq")
    public CustomResponse checkActiveMQ() {
        CustomResponse customResponse = new CustomResponse();
        try {
            customResponse = checkActiveMQService.judgeIsHealth();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return customResponse;
    }

}
