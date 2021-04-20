package com.wsn.service;

import com.wsn.entity.CustomResponse;
import com.wsn.untils.SendSimpleEmail;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by Lenovo on 2017/8/19.
 */
@Service
public class CheckWebServerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckWebServerService.class);

    @Autowired
    private Environment environment;
    @Autowired
    private SendSimpleEmail simpleEmail;

    /**
     * 根据响应对象进行判断并将相关信息封装成map
     * @return  服务器相关信息
     */
    public CustomResponse requestFireinfoUrl() throws Exception{
        boolean result =false;
        CustomResponse customResponse = new CustomResponse();
        String platformName = environment.getProperty("platform.name");//服务器所属平台
        String webServerUrl = environment.getProperty("web.requestUrl");
        String serviceHostAndPort = webServerUrl.split("/")[2];
        String serviceHost = serviceHostAndPort.split(":")[0];
        String subject = "WEB server exception";//异常邮件主题
        try {
            String port = serviceHostAndPort.split(":")[1];
            HttpResponse response = this.sendPost(webServerUrl);
            if (null != response && response.getStatusLine().getStatusCode() == 200) {
                result = true;
            }
            if (!result) {
                LOGGER.warn("WEB server is exception,Is now ready to send a notification message!");
                simpleEmail.sendTemplateEmail(subject, platformName,serviceHost,"WEB");
            }
            customResponse.setPort(port);
            customResponse.setResult(result);
            customResponse.setServiceHost(serviceHost);
            customResponse.setServerName("WEB");
        } catch (Exception e) {
            LOGGER.error("Verify that the WEB server has an exception");
            simpleEmail.sendTemplateEmail(subject, platformName,serviceHost,"WEB");
        }
        return customResponse;
    }

    /**
     * 请示指定路径获取响应对象
     * @param url   请求路径
     * @return  响应对象
     */
    private static HttpResponse sendPost(String url) {
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        HttpResponse httpResponse = null;
        try {
            httpResponse = httpclient.execute(httpPost);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return httpResponse;
    }
}
