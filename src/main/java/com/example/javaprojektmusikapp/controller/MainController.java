package com.example.javaprojektmusikapp.controller;

import com.example.javaprojektmusikapp.model.Playlist;
import com.example.javaprojektmusikapp.model.Song;
import com.example.javaprojektmusikapp.service.FavoritesService;
import com.example.javaprojektmusikapp.service.PlaylistService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class MainController
{
    // Controller der Sidebar-Navigation
    @FXML
    private NavigationController sidebarController;

    // Zentrales Content-Pane, in dem Views geladen werden
    @FXML
    private AnchorPane contentPane;

    // Controller der Playerbar unten
    @FXML
    private PlayerController playerbarController;

    // Top-Suchleiste (nur auf Home sichtbar)
    @FXML
    private HBox topSearchBar;

    @FXML
    private TextField topSearchField;

    // Referenz auf den aktuell geöffneten PlaylistController
    private PlaylistController currentPlaylistController;

    // Zentrale Services für Playlists und Favoriten
    private final PlaylistService playlistService = new PlaylistService();
    private final FavoritesService favoritesService = new FavoritesService();

    // Zugriff auf PlaylistService für andere Controller
    public PlaylistService getPlaylistService()
    {
        return playlistService;
    }

    // Zugriff auf FavoritesService für andere Controller
    public FavoritesService getFavoritesService()
    {
        return favoritesService;
    }

    // Initiale Einrichtung nach dem Laden von main.fxml
    @FXML
    public void initialize()
    {
        // MainController an die Sidebar übergeben
        sidebarController.setMainController(this);

        // Startansicht ist Home
        showHome();
    }

    // Lädt eine FXML-Datei in das zentrale Content-Pane
    private void loadView(String fxml)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/javaprojektmusikapp/" + fxml)
            );

            Node view = loader.load();
            Object controller = loader.getController();

            // HomeController mit MainController verbinden
            if (controller instanceof HomeController hc)
            {
                hc.setMainController(this);
            }

            // FavoritesController mit MainController verbinden
            if (controller instanceof FavoritesController fc)
            {
                fc.setMainController(this);
            }

            // SearchController mit MainController verbinden
            if (controller instanceof SearchController sc)
            {
                sc.setMainController(this);

                // Suchtext aus der Top-Suche übernehmen
                String query = topSearchField.getText();
                if (query != null && !query.isBlank())
                {
                    sc.setInitialQuery(query);
                }
            }

            // View im Content-Pane anzeigen
            contentPane.getChildren().setAll(view);

            // View auf volle Größe strecken
            AnchorPane.setTopAnchor(view, 0.0);
            AnchorPane.setBottomAnchor(view, 0.0);
            AnchorPane.setLeftAnchor(view, 0.0);
            AnchorPane.setRightAnchor(view, 0.0);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    // Zeigt die Home-Ansicht und die Top-Suchleiste
    public void showHome()
    {
        showTopSearchBar();
        loadView("home.fxml");
    }

    // Zeigt die Search-Ansicht ohne Top-Suchleiste
    public void showSearch()
    {
        hideTopSearchBar();
        loadView("search.fxml");
    }

    // Zeigt die Favorites-Ansicht ohne Top-Suchleiste
    public void showFavorites()
    {
        hideTopSearchBar();
        loadView("favorites.fxml");
    }

    // Zeigt die Genre-Ansicht für ein bestimmtes Genre
    public void showGenre(String genre)
    {
        hideTopSearchBar();

        try
        {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/javaprojektmusikapp/genre.fxml")
            );

            Node view = loader.load();

            GenreController controller = loader.getController();
            controller.setMainController(this);
            controller.setGenre(genre);

            contentPane.getChildren().setAll(view);

            AnchorPane.setTopAnchor(view, 0.0);
            AnchorPane.setBottomAnchor(view, 0.0);
            AnchorPane.setLeftAnchor(view, 0.0);
            AnchorPane.setRightAnchor(view, 0.0);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    // Zeigt die Playlist-Ansicht für eine ausgewählte Playlist
    public void showPlaylist(Playlist playlist)
    {
        hideTopSearchBar();

        try
        {
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
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    // Klick auf den Suchen-Button in der Top-Suchleiste
    @FXML
    private void onTopSearch()
    {
        String query = topSearchField.getText();

        if (query == null || query.isBlank())
        {
            return;
        }

        showSearch();
        topSearchField.clear();
    }

    // Blendet die Top-Suchleiste ein
    private void showTopSearchBar()
    {
        topSearchBar.setVisible(true);
        topSearchBar.setManaged(true);
    }

    // Blendet die Top-Suchleiste aus
    private void hideTopSearchBar()
    {
        topSearchBar.setVisible(false);
        topSearchBar.setManaged(false);
    }

    // Übergibt einen Song an die Playerbar
    public void showInPlayerbar(Song song)
    {
        if (playerbarController != null)
        {
            playerbarController.showSong(song);
        }
    }

    // Aktualisiert die aktuell geöffnete Playlist-Ansicht
    public void refreshCurrentPlaylist()
    {
        if (currentPlaylistController != null)
        {
            currentPlaylistController.refreshSongs();
        }
    }
}
