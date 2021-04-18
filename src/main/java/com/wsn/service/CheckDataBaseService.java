package com.wsn.service;

import com.wsn.entity.CustomResponse;
import com.wsn.untils.SendSimpleEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by Lenovo on 2017/8/19.
 */
@Service
public class CheckDataBaseService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckDataBaseService.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private Environment environment;
    @Autowired
    private SendSimpleEmail simpleEmail;

    public CustomResponse checkDataBase() throws Exception{
        boolean result = false;
        CustomResponse customResponse = new CustomResponse();
        try {
            String jdbcUrl = environment.getProperty("spring.datasource.url");
            String serviceHostAndPort = jdbcUrl.substring(jdbcUrl.indexOf("//") + 2, jdbcUrl.lastIndexOf("/"));
            String serviceHost = serviceHostAndPort.split(":")[0];
            String port = serviceHostAndPort.split(":")[1];
            String subject = "DataBase server exception";//异常邮件主题
            String text = new StringBuffer(serviceHost).append(" is exception").toString();//异常邮件内容

            String username = jdbcTemplate.queryForObject(environment.getProperty("mysql.sql"), String.class);
            if (environment.getProperty("mysql.expectResult").equals(username)) {
                result = true;
            }

            if (!result) {
                LOGGER.warn("DataBase server is exception,Is now ready to send a notification message!");
                simpleEmail.sendSimpleEmail(subject, text);
            }
            customResponse.setResult(result);
            customResponse.setServiceHost(serviceHost);
            customResponse.setServerName("数据库");
            customResponse.setPort(port);

        } catch (Exception e) {
            LOGGER.error("Verify that the DataBase server has an exception");
            throw e;
        }
        return customResponse;
    }


}
