package com.example.javaprojektmusikapp.ui.cell;

import com.example.javaprojektmusikapp.controller.MainController;
import com.example.javaprojektmusikapp.model.Song;
import com.example.javaprojektmusikapp.service.FavoritesService;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class FavoritesSongCell extends BaseSongCell
{
    // Service zur Verwaltung der Favoriten
    private final FavoritesService favoritesService;

    // Herz-Button zum Entfernen aus den Favoriten
    private final Button heartButton = new Button();

    // Platzhalter, um das Herz nach rechts zu schieben
    private final Region spacer = new Region();

    // Konstruktor bekommt den MainController
    public FavoritesSongCell(MainController mainController)
    {
        super(mainController);

        // FavoritesService über den MainController holen
        this.favoritesService = mainController.getFavoritesService();

        // Herzsymbol und CSS-Klassen setzen
        heartButton.setText("♥");
        heartButton.getStyleClass().addAll("heart-button", "favorite");

        // Spacer füllt den freien Platz zwischen Text und Herz
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Spacer und Herz zur bestehenden Root-HBox hinzufügen
        root.getChildren().add(spacer);
        root.getChildren().add(heartButton);

        // Klick auf Herz entfernt den Song aus den Favoriten
        heartButton.setOnAction(e ->
        {
            Song song = getItem();
            if (song == null)
            {
                return;
            }

            // Favoritenstatus umschalten
            favoritesService.toggleFavorite(song);

            // Favorites-View neu laden
            mainController.showFavorites();

            // Event nicht weiterreichen
            e.consume();
        });
    }
}
