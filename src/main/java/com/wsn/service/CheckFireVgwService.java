package com.wsn.service;

import com.wsn.entity.CustomResponse;
import com.wsn.untils.SendSimpleEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.Socket;

/**
 * Created by Lenovo on 2017/8/19.
 */
@Service
public class CheckFireVgwService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckFireVgwService.class);

    @Autowired
    private Environment environment;
    @Autowired
    private SendSimpleEmail simpleEmail;

    public boolean checkFireVgw(String host,Integer port,String message){
        Socket socket = null;
        try {
            socket = new Socket(host,port);
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter printWriter = new PrintWriter(outputStream);
            printWriter.write(message);
            printWriter.flush();

            InputStream inputStream = socket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = bufferedReader.readLine();
            if (null != line && (line.equals("connecttype:1")
                    || line.equals("connecttype:2")
                    || line.equals("connecttype:3")
                    || line.equals("connecttype:4"))) {
                return true;
            }
            bufferedReader.close();
            inputStream.close();
            printWriter.close();
            outputStream.close();
            return false;


        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            return false;
        }finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public CustomResponse judgeVgw() throws Exception{
        CustomResponse customResponse = new CustomResponse();
        String serviceHost = environment.getProperty("firevgw.serviceHost");
        String platformName = environment.getProperty("platform.name");//服务器所属平台
        String subject = "vgw server exception";//异常邮件主题
        try {
            Integer port = environment.getProperty("firevgw.port", Integer.class);
            String message = environment.getProperty("firevgw.message");
            boolean result = this.checkFireVgw(serviceHost, port, message);
            if (!result) {
                LOGGER.warn("FireVgw server is exception,Is now ready to send a notification message!");
                simpleEmail.sendTemplateEmail(subject, platformName,serviceHost,"Vgw");
            }
            customResponse.setServerName("虚拟网关");
            customResponse.setServiceHost(serviceHost);
            customResponse.setResult(result);
            customResponse.setPort(port.toString());
        } catch (Exception e) {
            LOGGER.error("Verify that the FireVgw server has an exception");
            simpleEmail.sendTemplateEmail(subject, platformName,serviceHost,"虚拟网关");
        }
        return customResponse;
    }

}
