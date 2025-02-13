package com.software.zapp.controllers;

import com.software.zapp.exceptions.ServiceException;
import com.software.zapp.services.ServiceInfo;
import com.software.zapp.services.impl.NgnixServer;
import com.software.zapp.utils.ServiceStatus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Text;

public class ServiceCardController {

    private boolean isServiceRunning = false;
    private NgnixServer ngnixServer;
    private ServiceInfo serviceConfig;

    @FXML
    private Text serviceName;
    @FXML
    private TextField serviceStatus;
    @FXML
    private Text servicePort;
    @FXML
    private Button startButton;
    @FXML
    private Button restartButton;
    @FXML
    private Button configureButton;
    @FXML
    private Button logsButton;

    @FXML
    public void initialize() {
        // Initialize with default configuration
        serviceConfig = new ServiceInfo("Ngnix Server", "444", "C:/Users/moham/OneDrive/Desktop/zapptest");
        ngnixServer = new NgnixServer(444, "C:/Users/moham/OneDrive/Desktop/zapptest");

        // Set the service initial status
        setServiceName(ngnixServer.getName());
        setServicePort(String.valueOf(ngnixServer.getPort()));
        serviceStatus.setText(ServiceStatus.STOPPED.toString());
        serviceStatus.setEditable(false);
    }

    public ServiceInfo getServiceInfo() {
        String portText = servicePort.getText().replace("Port: ", "");

        return new ServiceInfo(
                serviceName.getText(),
                portText,
                "C:/Users/moham/OneDrive/Desktop/zapptest"

        );
    }

    public void startService() throws ServiceException {
        if (!isServiceRunning) {
            // Start the service
            ngnixServer.start();
            setServiceName(ngnixServer.getName());
            setServicePort(String.valueOf(ngnixServer.getPort()));
            serviceStatus.setText(ServiceStatus.RUNNING.toString());
            startButton.setText("Stop");
            isServiceRunning = true;
        } else {
            // Stop the service
            ngnixServer.stop();
            serviceStatus.setText(ServiceStatus.STOPPED.toString());
            startButton.setText("Start");
            isServiceRunning = false;
        }
    }

    public void restartService(ActionEvent actionEvent) {
        // Restart the service
        System.out.println(actionEvent);
    }

    public void openConfiguration(ActionEvent actionEvent) {
        // Open the configuration window
        System.out.println(actionEvent);
    }

    public void openLogs(ActionEvent actionEvent) {
        // Open the logs window
        System.out.println(actionEvent);
    }

    public void setServiceName(String name) {
        serviceName.setText(name);
    }

    public void setServicePort(String port) {
        servicePort.setText("Port: " + port);
    }
}
