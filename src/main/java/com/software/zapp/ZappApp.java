package com.software.zapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ZappApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Load main view and icon
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/software/zapp/gui/main-view.fxml"));
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/software/zapp/images/zappIcon.PNG")), 100, 100, true, true);

        // Scene setup
        Scene scene = new Scene(fxmlLoader.load());
        scene.getRoot().setStyle("-fx-background-color: #FEF9E1");

        // Set stage properties
        stage.setResizable(false);
        stage.getIcons().add(icon);
        stage.setTitle("Zapp: Service Manager");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}