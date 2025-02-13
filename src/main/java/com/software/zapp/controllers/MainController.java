package com.software.zapp.controllers;

import com.software.zapp.services.ServiceInfo;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.io.IOException;

public class MainController {

    @FXML
    private Label label;
    @FXML
    private GridPane serviceCardsContainer;
    private static final double CARD_WIDTH = 300;
    private static final double CARD_HEIGHT = 200;


    public void initialize() {
        label.setText("Zapp Control Panel");

        serviceCardsContainer.widthProperty().addListener((obs, oldVal, newVal) -> {
            reorganizeGrid();
        });

        createServiceCard();
    }

    private void createServiceCard() {
       try {
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/software/zapp/gui/service-card.fxml"));
           Node serviceCard = loader.load();
           ServiceCardController cardController = loader.getController();

           ServiceInfo serviceInfo = cardController.getServiceInfo();
           cardController.setServiceName(serviceInfo.getName());
           cardController.setServicePort(String.valueOf(serviceInfo.getPort()));

           serviceCardsContainer.getChildren().add(serviceCard);
           reorganizeGrid();

       } catch (IOException e) {
           e.printStackTrace();
       }
    }

    private void reorganizeGrid() {
        double containerWidth = serviceCardsContainer.getWidth();
        int maxColumns = Math.max(1, (int)(containerWidth / CARD_WIDTH));

        // Reorganize existing cards
        ObservableList<Node> children = serviceCardsContainer.getChildren();
        for (int i = 0; i < children.size(); i++) {
            Node card = children.get(i);
            int row = i / maxColumns;
            int col = i % maxColumns;

            GridPane.setRowIndex(card, row);
            GridPane.setColumnIndex(card, col);
        }

        // Update column constraints
        serviceCardsContainer.getColumnConstraints().clear();
        double columnWidth = containerWidth / maxColumns;
        for (int i = 0; i < maxColumns; i++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setPrefWidth(columnWidth);
            serviceCardsContainer.getColumnConstraints().add(cc);
        }

        // Update row constraints
        serviceCardsContainer.getRowConstraints().clear();
        int numRows = (children.size() + maxColumns - 1) / maxColumns;
        for (int i = 0; i < numRows; i++) {
            RowConstraints rc = new RowConstraints();
            rc.setPrefHeight(CARD_HEIGHT);
            serviceCardsContainer.getRowConstraints().add(rc);
        }
    }

}
