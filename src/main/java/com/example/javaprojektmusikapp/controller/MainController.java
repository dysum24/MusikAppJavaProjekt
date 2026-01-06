package com.example.javaprojektmusikapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MainController {

    // Der Controller der Sidebar
    @FXML
    private NavigationController sidebarController;

    @FXML
    private AnchorPane contentPane;

    @FXML
    public void initialize() {
        sidebarController.setMainController(this);
        showHome();
    }

    private void loadView(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/javaprojektmusikapp/" + fxml)
            );
            Node view = loader.load();

            // Inhalt im Center austauschen
            contentPane.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showHome() {
        loadView("home.fxml");
    }

    public void showSearch() {
        loadView("search.fxml");
    }

    public void showFavorites() {
        loadView("favorites.fxml");
    }
}
