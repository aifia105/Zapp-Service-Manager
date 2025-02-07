package com.software.zapp.services;

import com.software.zapp.exceptions.ServiceException;

public abstract class EmbeddedServerService extends Service {
    public EmbeddedServerService(String name) {
        super(name);
    }

    @Override
    public void start() throws ServiceException {
        // TODO Auto-generated method stub
    }

    @Override
    public void stop() throws ServiceException {
        // TODO Auto-generated method stub
    }

    @Override
    public void restart() throws ServiceException {
        // TODO Auto-generated method stub
    }
}
