package com.wsn.service;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Created by Lenovo on 2017/8/19.
 */
@Service
public class CheckWebServerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckWebServerService.class);

    @Autowired
    private static Environment environment;

    public static boolean requestFireinfoUrl(String url) {
        HttpResponse response = sendPost(url);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 200) {
            return true;
        }
        return false;
    }

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
