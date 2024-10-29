package com.example.loadbalancer.model;

public class Server {
    private String host;
    private int port;

    public Server(String host, int port) {
        this.host = host;
        this.port = port;
    }

    // Getters and setters if needed

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    // Additional methods if needed
}
