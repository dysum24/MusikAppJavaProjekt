package com.example.javaprojektmusikapp.controller;

import com.example.javaprojektmusikapp.model.Song;
import com.example.javaprojektmusikapp.service.MusikService;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;

import java.util.List;

public class testController {

    @FXML
    private TextField searchField;

    @FXML
    private VBox searchDropdown;

    @FXML
    private ListView<String> resultsList;

    private MusikService musikService = new MusikService();

    @FXML
    public void initialize() {
        // Close dropdown when pressing Escape key
        searchField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                searchDropdown.setVisible(false);
            }
        });

        // Close dropdown when clicking somewhere else OR when window resizes
        searchDropdown.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                // Close on click outside
                newScene.setOnMouseClicked(event -> {
                    if (!searchDropdown.getBoundsInParent().contains(event.getX(), event.getY())) {
                        searchDropdown.setVisible(false);
                    }
                });

                // Close on resize (for maximize/restore buttons)
                newScene.widthProperty().addListener((o, oldW, newW) -> {
                    searchDropdown.setVisible(false);
                });
            }
        });
    }

    private void updateDropdownPosition() {
        double x = searchField.localToScene(0, 0).getX();
        double y = searchField.localToScene(0, 0).getY() + searchField.getHeight() + 5;
        searchDropdown.setLayoutX(x);
        searchDropdown.setLayoutY(y);
    }

    @FXML
    private void onSearch() {
        String suchbegriff = searchField.getText();

        if (suchbegriff.isEmpty()) {
            return;
        }

        try {
            List<Song> songs = musikService.searchByTitle(suchbegriff);

            resultsList.getItems().clear();

            for (Song song : songs) {
                resultsList.getItems().add(song.getTrackName() + " - " + song.getArtistName());
            }

            updateDropdownPosition();
            searchDropdown.setVisible(true);
            searchDropdown.toFront();

        } catch (Exception e) {
            System.out.println("Fehler bei der Suche: " + e.getMessage());
        }
    }
}