package com.software.zapp.services;

public class ServiceInfo {

    private final String name;
    private final String port;
    private final String path;

    public ServiceInfo(String name, String port, String path) {
        this.name = name;
        this.port = port;
        this.path = path;
    }

    public String getPort() {
        return port;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }
}
