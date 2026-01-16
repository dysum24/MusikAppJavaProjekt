package com.example.javaprojektmusikapp.controller;

import com.example.javaprojektmusikapp.model.Song;
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
    private PlayerController playerbarController;

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

            Object controller = loader.getController();
            if (controller instanceof SearchController sc) {
                sc.setMainController(this);
            }

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

    public void showInPlayerbar(Song song) {
        if (playerbarController != null) {
            playerbarController.showSong(song);
        }
    }
}
