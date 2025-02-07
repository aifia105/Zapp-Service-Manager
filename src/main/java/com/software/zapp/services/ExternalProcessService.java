package com.software.zapp.services;

import com.software.zapp.exceptions.ServiceException;
import com.software.zapp.utils.ServiceStatus;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.net.ServerSocket;
import java.util.List;

public abstract class ExternalProcessService extends Service{
    private Process process;
    private List<String> command;
    private String logs;
    private final StringProperty logsProperty = new SimpleStringProperty();

    public ExternalProcessService(String name) {
        super(name);
    }

    @Override
    public void start() throws ServiceException {
        try {
            ProcessBuilder builder = new ProcessBuilder(command);
            builder.redirectErrorStream(true);
            this.process = builder.start();
        } catch (Exception e) {
            setStatus(ServiceStatus.FAILED);
            throw new ServiceException("Failed to start external process", e);
        }
    }

    @Override
    public void stop() throws ServiceException {
       try {
           if (this.getProcess() != null) {
               this.getProcess().destroy();
           }
       } catch (Exception e) {
           setStatus(ServiceStatus.FAILED);
           throw new ServiceException("Failed to stop external process", e);
       }
    }

    @Override
    public void restart() throws ServiceException {
       try {
           stop();
           start();
       } catch (Exception e) {
           setStatus(ServiceStatus.FAILED);
           throw new ServiceException("Failed to restart external process", e);
       }
    }

    protected boolean isPortFree(int port) {
        try (ServerSocket socket = new ServerSocket(port)) {
            return true;
        } catch (Exception e) {
            return false;

        }
    }


    public Process getProcess() {
        return process;
    }
    public void setProcess(Process process) {
        this.process = process;
    }

    public List<String> getCommand() {
        return command;
    }
    public void setCommand(List<String> command) {
        this.command = command;
    }

    public String getLogs() {
        return logs;
    }
    public void setLogs(String logs) {
        this.logs = logs;
        this.logsProperty.set(logs);
    }
}
