package com.wsn.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by Lenovo on 2017/8/19.
 */
@Service
public class CheckDataBaseService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckDataBaseService.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean checkDataBase(String mysqlUrl,String expectResult) {
        String username = jdbcTemplate.queryForObject(mysqlUrl, String.class);
        if (expectResult.equals(username)) {
            return true;
        }
        return false;
    }


}
