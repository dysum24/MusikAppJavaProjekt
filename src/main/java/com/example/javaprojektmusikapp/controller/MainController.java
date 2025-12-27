package com.example.javaprojektmusikapp.controller;

import com.example.javaprojektmusikapp.model.Song;
import com.example.javaprojektmusikapp.service.MusikService;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.List;

public class MainController {

    @FXML
    private TextField searchField;

    @FXML
    private ListView<String> resultsList;

    private final MusikService musikService = new MusikService();

    @FXML
    private void onSearch() {
        String query = searchField.getText();
        if (query.isEmpty()) return;

        try {
            List<Song> songs = musikService.searchByTitle(query);
            resultsList.getItems().clear();
            for (Song song : songs) {
                resultsList.getItems().add(song.getTrackName() + " - " + song.getArtistName());
            }
        } catch (Exception e) {
            resultsList.getItems().clear();
            resultsList.getItems().add("Error: " + e.getMessage());
        }
    }
}