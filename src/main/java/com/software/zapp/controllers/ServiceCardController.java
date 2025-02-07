package com.software.zapp.controllers;

import com.software.zapp.utils.ServiceStatus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Text;

public class ServiceCardController {

    private boolean isServiceRunning = false;

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
        // Set buttons tooltips
        startButton.setTooltip(new Tooltip("Click to start/stop the service"));
        restartButton.setTooltip(new Tooltip("Click to restart the service"));
        configureButton.setTooltip(new Tooltip("Click to configure the service"));
        logsButton.setTooltip(new Tooltip("Click to view logs for the service"));

        // Set the service initial status
        serviceStatus.setText(ServiceStatus.STOPPED.toString());
    }

    public void startService(ActionEvent actionEvent) {
       if(!isServiceRunning){
              // Stop the service
              serviceStatus.setText(ServiceStatus.RUNNING.toString());
              startButton.setText("Stop");
              isServiceRunning = true;
         } else {
              // Start the service
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
