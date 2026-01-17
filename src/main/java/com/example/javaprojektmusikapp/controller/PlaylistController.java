package com.example.javaprojektmusikapp.controller;

import com.example.javaprojektmusikapp.model.Playlist;
import com.example.javaprojektmusikapp.model.Song;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;

public class PlaylistController {

    @FXML
    private Label playlistTitle;

    @FXML
    private ListView<Song> songList;

    private MainController mainController;

    private Playlist playlist;

    public Playlist getPlaylist() {
        return playlist;
    }


    @FXML
    private void initialize() {

        songList.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(Song song, boolean empty) {
                super.updateItem(song, empty);

                if (empty || song == null) {
                    setText(null);
                    setContextMenu(null);
                    return;
                }

                setText(song.getTrackName() + "\n" + song.getArtistName());

                MenuItem removeItem = new MenuItem("Aus Playlist entfernen");

                removeItem.setOnAction(e -> {
                    mainController.getPlaylistService()
                            .removeSongFromPlaylist(playlist, song);

                    refreshSongs();
                });

                setContextMenu(new javafx.scene.control.ContextMenu(removeItem));
            }
        });


        songList.setOnMouseClicked(event -> {
            if (event.getButton() == javafx.scene.input.MouseButton.PRIMARY) {
                Song selected = songList.getSelectionModel().getSelectedItem();
                if (selected != null && mainController != null) {
                    mainController.showInPlayerbar(selected);
                }
            }
        });

    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
        playlistTitle.setText(playlist.getName());
        songList.getItems().setAll(playlist.getSongs());
    }

    public void refreshSongs() {
        songList.getItems().setAll(playlist.getSongs());
    }

}
