package com.example.javaprojektmusikapp.controller;

import com.example.javaprojektmusikapp.model.Song;
import com.example.javaprojektmusikapp.service.FavoritesService;
import com.example.javaprojektmusikapp.ui.cell.FavoritesSongCell;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class FavoritesController
{
    // ListView, in der alle Favoriten angezeigt werden
    @FXML
    private ListView<Song> favoritesList;

    // Service, der die Favoriten verwaltet (laden, speichern, entfernen)
    private FavoritesService favoritesService;

    // Referenz auf den MainController für Navigation und Services
    private MainController mainController;

    // Wird vom MainController nach dem Laden der View aufgerufen
    public void setMainController(MainController mainController)
    {
        // MainController speichern
        this.mainController = mainController;

        // FavoritesService aus dem MainController holen
        this.favoritesService = mainController.getFavoritesService();

        // Eigene SongCell für Favoriten setzen
        favoritesList.setCellFactory(list ->
                new FavoritesSongCell(this.mainController)
        );

        // Favoriten beim Öffnen der View laden
        refreshFavorites();
    }

    // Lädt alle aktuellen Favoriten aus dem Service neu in die ListView
    public void refreshFavorites()
    {
        favoritesList.getItems().setAll(
                favoritesService.getFavorites()
        );
    }
}
