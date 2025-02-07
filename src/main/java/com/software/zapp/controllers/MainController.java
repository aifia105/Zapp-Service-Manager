package com.software.zapp.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class MainController {

    @FXML
    private Label label;
    @FXML
    private GridPane serviceCardsContainer;
    private int currentRow = 0;
    private int currentColumn = 0;


    public void initialize() {
        label.setText("Zapp Control Panel");

        createServiceCard("Nginx Server", "80");
        createServiceCard("MySQL Database", "3306");
        createServiceCard("PHP Server", "9000");
        createServiceCard("Node.js Server", "3000");
    }

    private void createServiceCard(String serviceName, String servicePort) {
       try {
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/software/zapp/gui/service-card.fxml"));
           Node serviceCard = loader.load();
           ServiceCardController cardController = loader.getController();
           cardController.setServiceName(serviceName);
           cardController.setServicePort(servicePort);

           serviceCardsContainer.add(serviceCard, currentColumn, currentRow);

           currentColumn++;
           if (currentColumn > 1) {
               currentColumn = 0;
               currentRow++;
           }

       } catch (IOException e) {
           e.printStackTrace();
       }
    }
}
