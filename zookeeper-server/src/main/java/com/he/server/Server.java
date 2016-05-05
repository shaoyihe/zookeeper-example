package com.he.server;

/**
 * Created by heshaoyi on 5/4/16.
 */
public class Server {
    private String protrol;
    private String host;
    private int port;

    public Server(String protrol, String host, int port) {
        this.protrol = protrol;
        this.host = host;
        this.port = port;
    }

    public String getProtrol() {
        return protrol;
    }

    public void setProtrol(String protrol) {
        this.protrol = protrol;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
