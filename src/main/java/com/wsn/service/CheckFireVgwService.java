package com.wsn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.client.WebSocketClient;

import java.io.*;
import java.net.Socket;

/**
 * Created by Lenovo on 2017/8/19.
 */
@Service
public class CheckFireVgwService {

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
            if (null != line && (line.equals("connecttype:1") || line.equals("connecttype:2") || line.equals
                    ("connecttype:3")
                    || line.equals("connecttype:4"))) {
                return true;
            }
            bufferedReader.close();
            inputStream.close();
            printWriter.close();
            outputStream.close();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
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

}
