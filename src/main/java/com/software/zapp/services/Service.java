package com.software.zapp.services;

import com.software.zapp.exceptions.ServiceException;
import com.software.zapp.utils.ServiceStatus;

public abstract class Service {
    private String name;
    private volatile ServiceStatus status;
    private volatile String pid;

    public Service(String name) {
        this.status = ServiceStatus.STOPPED;
        this.name = name;
    }


    public abstract void start() throws ServiceException;
    public abstract void stop() throws ServiceException;
    public abstract void restart() throws ServiceException;





    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public ServiceStatus getStatus() {
        return status;
    }
    public void setStatus(ServiceStatus status) {
        this.status = status;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

}
