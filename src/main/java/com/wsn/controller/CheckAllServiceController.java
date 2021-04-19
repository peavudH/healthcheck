package com.wsn.controller;

import com.wsn.entity.CustomResponse;
import com.wsn.service.CheckActiveMQService;
import com.wsn.service.CheckDataBaseService;
import com.wsn.service.CheckFireVgwService;
import com.wsn.service.CheckWebServerService;
import com.wsn.untils.SendSimpleEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Lenovo
 * Date: 2017/9/3
 * Time: 9:17
 * Description:
 */
@Controller
@RequestMapping("/healthcheck")
public class CheckAllServiceController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckAllServiceController.class);

    @Autowired
    private SendSimpleEmail sendSimpleEmail;
    @Autowired
    private CheckWebServerService checkWebServerService;
    @Autowired
    private CheckDataBaseService checkDataBaseService;
    @Autowired
    private CheckActiveMQService checkActiveMQService;
    @Autowired
    private CheckFireVgwService checkFireVgwService;

    @RequestMapping("/checkallservice")
    public String checkAllService(ModelMap model) {
        List<CustomResponse> list = new ArrayList<>();
        try {
            checkService(list);
            model.addAttribute("list", list);
            model.addAttribute("name", "momo");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            try {
                sendSimpleEmail.sendSimpleEmail("healthCheck is exception",e.toString());
            } catch (Exception ex) {
                LOGGER.error(ex.getMessage());
            }
        }
        return "index";
    }

    @RequestMapping("/ajaxrequest")
    public List<CustomResponse> ajaxrequest() {
        List<CustomResponse> list = new ArrayList<>();
        try {
            checkService(list);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            try {
                sendSimpleEmail.sendSimpleEmail("healthCheck is exception",e.toString());
            } catch (Exception ex) {
                LOGGER.error(ex.getMessage());
            }
        }
        return list;
    }


    /**
     * 检查服务器
     * @param list
     * @throws Exception
     */
    private void checkService(List<CustomResponse> list) throws Exception {
        list.add(checkActiveMQService.judgeIsHealth());
        list.add(checkWebServerService.requestFireinfoUrl());
        list.add(checkDataBaseService.checkDataBase());
        list.add(checkFireVgwService.judgeVgw());
    }
}
