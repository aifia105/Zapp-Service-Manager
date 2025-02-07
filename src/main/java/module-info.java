module com.software.zapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires commons.io;

    opens com.software.zapp to javafx.fxml;
    opens com.software.zapp.controllers to javafx.fxml;
    exports com.software.zapp;
}