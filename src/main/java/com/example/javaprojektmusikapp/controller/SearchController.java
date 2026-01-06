package com.example.javaprojektmusikapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class SearchController {

    @FXML
    private TextField searchField;

    @FXML
    private ListView<String> resultsList;

    @FXML
    private void onSearch() {

        String query = searchField.getText();

        resultsList.getItems().clear();

        // Wenn nichts eingegeben dann Liste ausblenden
        if (query == null || query.isBlank()) {
            resultsList.setVisible(false);
            resultsList.setManaged(false);
            return;
        }

        // Dummy-Ergebnisse
        resultsList.getItems().add("Song: " + query);
        resultsList.getItems().add("Artist: " + query);
        resultsList.getItems().add("Album: " + query);

        // Liste anzeigen
        resultsList.setVisible(true);
        resultsList.setManaged(true);
    }

}
