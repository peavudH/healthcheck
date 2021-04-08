package com.wsn.controller;

import com.wsn.service.CheckActiveMQService;
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
public class CheckActiveMQController {

    @Autowired
    private CheckActiveMQService checkActiveMQService;
    @Autowired
    private Receiver receiver;

    @PostMapping("/checkactivemq")
    public void checkActiveMQ() {
        try {
            checkActiveMQService.run(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
