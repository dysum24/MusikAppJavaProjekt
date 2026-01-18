package com.example.javaprojektmusikapp.ui.cell;

import com.example.javaprojektmusikapp.controller.MainController;
import com.example.javaprojektmusikapp.model.Playlist;
import com.example.javaprojektmusikapp.model.Song;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

public class PlaylistSongCell extends BaseSongCell
{
    // Referenz auf die Playlist, aus der Songs entfernt werden können
    private final Playlist playlist;

    // Konstruktor bekommt MainController und die zugehörige Playlist
    public PlaylistSongCell(MainController mainController, Playlist playlist)
    {
        super(mainController);
        this.playlist = playlist;

        // ContextMenu für Rechtsklick auf einen Song
        ContextMenu menu = new ContextMenu();

        // Menüpunkt zum Entfernen eines Songs aus der Playlist
        MenuItem removeItem = new MenuItem("Aus Playlist entfernen");

        removeItem.setOnAction(e ->
        {
            Song song = getItem();
            if (song == null)
            {
                return;
            }

            // Song aus der Playlist entfernen
            mainController.getPlaylistService()
                    .removeSongFromPlaylist(playlist, song);

            // Aktuelle Playlist-Ansicht aktualisieren
            mainController.refreshCurrentPlaylist();
        });

        // Menüpunkt zum ContextMenu hinzufügen
        menu.getItems().add(removeItem);

        // ContextMenu der Zelle setzen
        setContextMenu(menu);
    }
}
