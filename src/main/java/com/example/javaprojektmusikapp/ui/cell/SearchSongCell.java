package com.example.javaprojektmusikapp.ui.cell;

import com.example.javaprojektmusikapp.controller.MainController;
import com.example.javaprojektmusikapp.model.Playlist;
import com.example.javaprojektmusikapp.model.Song;
import com.example.javaprojektmusikapp.service.FavoritesService;
import com.example.javaprojektmusikapp.service.PlaylistService;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

import java.util.List;
import java.util.Optional;

public class SearchSongCell extends BaseSongCell
{
    // Service für Favoriten-Verwaltung
    private final FavoritesService favoritesService;

    // Service für Playlist-Verwaltung
    private final PlaylistService playlistService;

    // Coverbild des Songs
    private final ImageView coverImage = new ImageView();

    // Herz-Button zum Hinzufügen/Entfernen aus Favoriten
    private final Button heartButton = new Button();

    // Platzhalter, um UI-Elemente auseinanderzuschieben
    private final Region spacer = new Region();

    // Konstruktor bekommt den MainController
    public SearchSongCell(MainController mainController)
    {
        super(mainController);

        // Services über den MainController beziehen
        this.favoritesService = mainController.getFavoritesService();
        this.playlistService = mainController.getPlaylistService();

        // Größe und Verhalten des Coverbildes
        coverImage.setFitWidth(40);
        coverImage.setFitHeight(40);
        coverImage.setPreserveRatio(true);

        // CSS-Klasse für den Herz-Button
        heartButton.getStyleClass().add("heart-button");

        // Spacer nimmt den verfügbaren Platz ein
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // UI-Elemente zur bestehenden Root-HBox hinzufügen
        root.getChildren().add(0, coverImage);
        root.getChildren().add(spacer);
        root.getChildren().add(heartButton);

        // Klick auf Herz toggelt Favoritenstatus
        heartButton.setOnAction(e ->
        {
            Song song = getItem();
            if (song == null)
            {
                return;
            }

            favoritesService.toggleFavorite(song);
            updateHeart(song);

            // Event nicht an die ListCell weitergeben
            e.consume();
        });

        // ContextMenu für Rechtsklick auf einen Song
        ContextMenu menu = new ContextMenu();

        // Menüpunkt zum Hinzufügen zu einer Playlist
        MenuItem addToPlaylist =
                new MenuItem("Zu Playlist hinzufügen…");

        addToPlaylist.setOnAction(e ->
        {
            Song song = getItem();
            if (song == null)
            {
                return;
            }

            // Alle vorhandenen Playlists holen
            List<Playlist> playlists =
                    playlistService.getAllPlaylists();

            if (playlists.isEmpty())
            {
                return;
            }

            // Auswahl-Dialog für die Playlist
            ChoiceDialog<Playlist> dialog =
                    new ChoiceDialog<>(playlists.get(0), playlists);

            Optional<Playlist> result = dialog.showAndWait();
            result.ifPresent(p ->
                    playlistService.addSongToPlaylist(p, song)
            );
        });

        // ContextMenu setzen
        menu.getItems().add(addToPlaylist);
        setContextMenu(menu);
    }

    @Override
    protected void updateItem(Song song, boolean empty)
    {
        super.updateItem(song, empty);

        if (empty || song == null)
        {
            return;
        }

        // Herz-Button je nach Favoritenstatus aktualisieren
        updateHeart(song);

        // Coverbild laden, falls vorhanden
        if (song.getArtworkUrl() != null && !song.getArtworkUrl().isBlank())
        {
            coverImage.setImage(
                    new Image(song.getArtworkUrl(), true)
            );
        }
        else
        {
            coverImage.setImage(null);
        }
    }

    // Aktualisiert Darstellung des Herz-Buttons
    private void updateHeart(Song song)
    {
        heartButton.getStyleClass().setAll("heart-button");

        if (favoritesService.isFavorite(song))
        {
            heartButton.setText("♥");
            heartButton.getStyleClass().add("favorite");
        }
        else
        {
            heartButton.setText("♡");
        }
    }
}
