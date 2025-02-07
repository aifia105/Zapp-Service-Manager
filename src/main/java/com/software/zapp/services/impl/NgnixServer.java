package com.software.zapp.services.impl;

import org.apache.commons.io.FileUtils;
import com.software.zapp.exceptions.ServiceException;
import com.software.zapp.services.ExternalProcessService;
import com.software.zapp.utils.ServiceStatus;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class NgnixServer extends ExternalProcessService {
    private static final String NGINX_DIR_NAME = "nginx";
    private Path tempDir;
    private int port;
    private String documentRoot;

    public NgnixServer(int port, String documentRoot) {
        super("Ngnix Server");
        this.port = port;
        this.documentRoot = documentRoot;
    }

    @Override
    public void start() throws ServiceException {
        try {
            System.out.println("Starting Ngnix Server.......");
            extractNginx();
            setupDocumentRoot();
            setNginxConfig();
            if (!isPortFree(this.port)) {
                throw new ServiceException("Port: " + this.port + " is already in use");
            }
            String nginxExe = tempDir.resolve("nginx.exe").toString();
            List<String> command = List.of(nginxExe, "-p", tempDir.toString(), "-c", "conf/nginx.conf");
            this.setCommand(command);
            super.start();
            this.setPid(String.valueOf(this.getProcess().pid()));
            this.setStatus(ServiceStatus.RUNNING);
            System.out.println("Ngnix Server started.........");
        } catch (Exception e) {
            this.setStatus(ServiceStatus.FAILED);
            this.setPid(null);
            super.stop();
            throw new ServiceException("Failed to start Ngnix Server", e);
        }
    }


    @Override
    public void stop() throws ServiceException {
        try {
            System.out.println("Stopping Ngnix Server.......");

            if (this.getProcess() != null) {
                this.getProcess().destroy();
            }

            ProcessBuilder taskKill = new ProcessBuilder("taskkill", "/F", "/IM", "nginx.exe");
            taskKill.start().waitFor();

            this.setStatus(ServiceStatus.STOPPED);
            this.setPid(null);
            this.setPort(0);
            System.out.println("Ngnix Server stopped.........");
        } catch (Exception e) {
            this.setStatus(ServiceStatus.FAILED);
            throw new ServiceException("Failed to stop Ngnix Server", e);
        }
    }

    @Override
    public void restart() throws ServiceException {
        try {
            System.out.println("Restarting Ngnix Server.......");
            super.restart();
            System.out.println("Ngnix Server restarted.........");
        } catch (Exception e) {
            this.setStatus(ServiceStatus.FAILED);
            throw new ServiceException("Failed to restart Ngnix Server", e);
        }
    }


    public void ngnixLogs() throws ServiceException {
        try {
            Path accessLogPath = tempDir.resolve("logs/access.log");
            Path errorLogPath = tempDir.resolve("logs/error.log");

            StringBuilder logs = new StringBuilder();

            if (Files.exists(errorLogPath)) {
                logs.append("=== ERROR LOGS ===\n");
                List<String> errorLogs = Files.readAllLines(errorLogPath);
                int startIndex = Math.max(0, errorLogs.size() - 100);
                errorLogs.subList(startIndex, errorLogs.size()).forEach(line -> logs.append(line).append("\n"));
            }

            if (Files.exists(accessLogPath)) {
                logs.append("=== ACCESS LOGS ===\n");
                List<String> accessLogs = Files.readAllLines(accessLogPath);
                int startIndex = Math.max(0, accessLogs.size() - 100);
                accessLogs.subList(startIndex, accessLogs.size()).forEach(line -> logs.append(line).append("\n"));
            }

            if (logs.isEmpty()) {
                logs.append("No logs found");
            }
            this.setLogs(logs.toString());
        } catch (Exception e) {
            throw new ServiceException("Failed to get logs for Ngnix Server", e);
        }
    }

    public boolean isNginxRunning() throws ServiceException {
        try {
            String line;
            ProcessBuilder builder = new ProcessBuilder("tasklist");
            Process process = builder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((line = reader.readLine()) != null) {
                if (line.toLowerCase().contains("nginx.exe")) {
                    return true;
                }
            }
        } catch (Exception e) {
            throw new ServiceException("Failed to check process status", e);
        }
        return false;
    }

    private void extractNginx() throws ServiceException {
        try {
            tempDir = Files.createTempDirectory("zapp-nginx").toAbsolutePath();
            FileUtils.copyDirectory(
                    new File(Objects.requireNonNull(getClass().getClassLoader().getResource(NGINX_DIR_NAME)).toURI()),
                    tempDir.toFile()
            );
            Files.createDirectories(tempDir.resolve("logs"));
            Files.createDirectories(tempDir.resolve("temp/client_body_temp"));
            Files.createDirectories(tempDir.resolve("temp/proxy_temp"));
            Files.createDirectories(tempDir.resolve("temp/fastcgi_temp"));
            Files.createDirectories(tempDir.resolve("temp/uwsgi_temp"));
            Files.createDirectories(tempDir.resolve("temp/scgi_temp"));
        } catch (Exception e) {
            throw new ServiceException("Failed to extract Ngnix Server", e);
        }
    }

    private void setNginxConfig() throws ServiceException {
        try {
            Path configPath = tempDir.resolve("conf/nginx.conf");
            String config = Files.readString(configPath);
            config = config.replace("${PORT}", String.valueOf(this.port)).replace("${DOCUMENT_ROOT}", this.documentRoot);
            Files.writeString(configPath, config);
        } catch (Exception e) {
            throw new ServiceException("Failed to set Ngnix Server configuration", e);
        }
    }

    private void setupDocumentRoot() throws ServiceException {
        try {
            Path wwwPath = tempDir.resolve("www");
            if (!Files.exists(wwwPath)) {
                Files.createDirectory(wwwPath);
            }
            FileUtils.copyDirectory(new File(this.documentRoot), wwwPath.toFile());
            this.documentRoot = wwwPath.toString();
        } catch (Exception e) {
            throw new ServiceException("Failed to setup document root", e);
        }
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDocumentRootPath() {
        return documentRoot;
    }

    public void setDocumentRootPath(String documentRoot) {
        this.documentRoot = documentRoot;
    }

    public static void main(String[] args) {
        NgnixServer ngnixServer = new NgnixServer(4444, "C:/Users/moham/OneDrive/Desktop/zapptest");
        try {
            System.out.println("Nginx is running: " + ngnixServer.isNginxRunning());
           // ngnixServer.start();
            System.out.println("Nginx is running: " + ngnixServer.isNginxRunning());
            ngnixServer.stop();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }


}
