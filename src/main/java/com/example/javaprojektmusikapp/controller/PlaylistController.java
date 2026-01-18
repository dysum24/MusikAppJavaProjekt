package com.example.javaprojektmusikapp.controller;

import com.example.javaprojektmusikapp.model.Playlist;
import com.example.javaprojektmusikapp.model.Song;
import com.example.javaprojektmusikapp.ui.cell.PlaylistSongCell;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class PlaylistController
{
    // Überschrift mit dem Namen der aktuell geöffneten Playlist
    @FXML
    private Label playlistTitle;

    // ListView mit allen Songs der Playlist
    @FXML
    private ListView<Song> songList;

    // Referenz auf den MainController für Services und Aktualisierung
    private MainController mainController;

    // Aktuell angezeigte Playlist
    private Playlist playlist;

    @FXML
    private void initialize()
    {
    }

    // Wird vom MainController gesetzt
    public void setMainController(MainController mainController)
    {
        this.mainController = mainController;
    }

    // Setzt die Playlist und lädt deren Songs
    public void setPlaylist(Playlist playlist)
    {
        this.playlist = playlist;

        // Playlist-Namen als Überschrift setzen
        playlistTitle.setText(playlist.getName());

        // Songs der Playlist in die ListView laden
        songList.getItems().setAll(playlist.getSongs());

        // Eigene SongCell für Playlist-Songs setzen
        songList.setCellFactory(list ->
                new PlaylistSongCell(mainController, playlist)
        );
    }

    // Aktualisiert die Songliste der aktuellen Playlist
    public void refreshSongs()
    {
        songList.getItems().setAll(
                playlist.getSongs()
        );
    }
}
