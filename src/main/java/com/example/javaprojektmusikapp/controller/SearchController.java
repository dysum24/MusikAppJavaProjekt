package com.example.javaprojektmusikapp.controller;

import com.example.javaprojektmusikapp.model.Song;
import com.example.javaprojektmusikapp.service.MusikService;
import com.example.javaprojektmusikapp.ui.cell.SearchSongCell;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class SearchController
{
    // TextField für die Suchanfrage
    @FXML
    private TextField searchField;

    // ListView für die Suchergebnisse
    @FXML
    private ListView<Song> resultsList;

    // Referenz auf den MainController für Services und Player
    private MainController mainController;

    // Service für die Suche über die iTunes API
    private final MusikService musikService = new MusikService();

    @FXML
    private void initialize()
    {
        // Ergebnisliste initial ausblenden
        resultsList.setVisible(false);
        resultsList.setManaged(false);
        resultsList.getItems().clear();
    }

    // Wird vom MainController gesetzt
    public void setMainController(MainController mainController)
    {
        this.mainController = mainController;

        // Eigene SongCell für Suchergebnisse setzen
        resultsList.setCellFactory(
                list -> new SearchSongCell(this.mainController)
        );
    }

    // Wird beim Klick auf den Suchen-Button ausgeführt
    @FXML
    private void onSearch()
    {
        String query = searchField.getText();
        resultsList.getItems().clear();

        // Leere Suche dann keine Ergebnisse anzeigen
        if (query == null || query.isBlank())
        {
            resultsList.setVisible(false);
            resultsList.setManaged(false);
            return;
        }

        try
        {
            // Songs anhand der Suchanfrage laden
            resultsList.getItems().setAll(
                    musikService.searchByTitle(query)
            );

            // Ergebnisliste anzeigen
            resultsList.setVisible(true);
            resultsList.setManaged(true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    // Übernimmt den Suchtext aus der Top-Suchleiste
    public void setInitialQuery(String query)
    {
        searchField.setText(query);
        onSearch();
    }
}
