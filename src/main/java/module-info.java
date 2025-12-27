module com.example.javaprojektmusikapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires com.google.gson;
    requires java.net.http;

    opens com.example.javaprojektmusikapp to javafx.fxml;
    opens com.example.javaprojektmusikapp.controller to javafx.fxml;
    exports com.example.javaprojektmusikapp;

}