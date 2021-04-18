package com.wsn.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 * User: Lenovo
 * Date: 2017/9/3
 * Time: 11:36
 * Description:
 */
@RestController
public class PageController {

    @RequestMapping("/healthcheck")
    public String indexPage() {
        return "index";
    }
}
