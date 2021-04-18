package com.wsn.entity;

/**
 * Created with IntelliJ IDEA.
 * User: Lenovo
 * Date: 2017/9/3
 * Time: 12:36
 * Description:
 */
public class CustomResponse {

    private String serverName;
    private String serviceHost;
    private String port;
    private boolean result;

    public CustomResponse() {
        super();
    }

    public CustomResponse(String serverName, String serviceHost, String port, boolean result) {
        this.serverName = serverName;
        this.serviceHost = serviceHost;
        this.port = port;
        this.result = result;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServiceHost() {
        return serviceHost;
    }

    public void setServiceHost(String serviceHost) {
        this.serviceHost = serviceHost;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
