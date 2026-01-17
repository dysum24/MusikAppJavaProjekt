package com.example.javaprojektmusikapp.controller;

import com.example.javaprojektmusikapp.model.Song;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import com.example.javaprojektmusikapp.model.Playlist;
import com.example.javaprojektmusikapp.controller.PlaylistController;
import javafx.scene.layout.AnchorPane;
import com.example.javaprojektmusikapp.service.PlaylistService;
import java.io.IOException;

public class MainController {

    // Der Controller der Sidebar
    @FXML
    private NavigationController sidebarController;

    @FXML
    private AnchorPane contentPane;

    @FXML
    private PlayerController playerbarController;

    private PlaylistController currentPlaylistController;

    private final PlaylistService playlistService = new PlaylistService();

    public PlaylistService getPlaylistService() {
        return playlistService;
    }

    @FXML
    public void initialize() {
        sidebarController.setMainController(this);
        showHome();
    }

    private void loadView(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/javaprojektmusikapp/" + fxml)
            );
            Node view = loader.load();

            Object controller = loader.getController();

            if (controller instanceof SearchController sc) {
                sc.setMainController(this);
            }

            if (controller instanceof FavoritesController fc) {
                fc.setMainController(this);
            }

            contentPane.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void showHome() {
        loadView("home.fxml");
    }

    public void showSearch() {
        loadView("search.fxml");
    }

    public void showFavorites() {
        loadView("favorites.fxml");
    }

    public void showInPlayerbar(Song song) {
        if (playerbarController != null) {
            playerbarController.showSong(song);
        }
    }

    public void showPlaylist(Playlist playlist) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/javaprojektmusikapp/playlist.fxml")
            );
            Node view = loader.load();

            currentPlaylistController = loader.getController();
            currentPlaylistController.setMainController(this);
            currentPlaylistController.setPlaylist(playlist);

            contentPane.getChildren().setAll(view);

            AnchorPane.setTopAnchor(view, 0.0);
            AnchorPane.setBottomAnchor(view, 0.0);
            AnchorPane.setLeftAnchor(view, 0.0);
            AnchorPane.setRightAnchor(view, 0.0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshCurrentPlaylist() {
        if (currentPlaylistController != null) {
            currentPlaylistController.refreshSongs();
        }
    }


}
