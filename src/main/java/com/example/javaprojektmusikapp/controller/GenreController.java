package com.example.javaprojektmusikapp.controller;

import com.example.javaprojektmusikapp.model.Song;
import com.example.javaprojektmusikapp.service.MusikService;
import com.example.javaprojektmusikapp.ui.cell.GenreSongCell;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class GenreController
{
    // Überschrift, die den aktuell geöffneten Genre-Namen anzeigt
    @FXML
    private Label genreTitle;

    // ListView, in der die Songs des Genres angezeigt werden
    @FXML
    private ListView<Song> songList;

    // Referenz auf den MainController für Navigation und Player
    private MainController mainController;

    // Service zum Laden der Songs aus der iTunes API
    private final MusikService musikService = new MusikService();

    // Wird vom MainController gesetzt, nachdem die View geladen wurde
    public void setMainController(MainController mainController)
    {
        // MainController speichern
        this.mainController = mainController;

        // Eigene SongCell für Genre-Songs setzen
        songList.setCellFactory(list ->
                new GenreSongCell(this.mainController)
        );
    }

    // Setzt den Genre-Namen und lädt die passenden Songs
    public void setGenre(String genre)
    {
        // Genre-Überschrift setzen
        if (genreTitle != null)
        {
            genreTitle.setText(genre);
        }

        try
        {
            // Songs anhand des Genre-Namens laden
            songList.getItems().setAll(
                    musikService.searchByTitle(genre)
            );
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize()
    {
    }

    // Zurück zur Home-Ansicht wechseln
    @FXML
    private void onBack()
    {
        if (mainController != null)
        {
            mainController.showHome();
        }
    }
}
