package com.example.javaprojektmusikapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.css.PseudoClass;
import com.example.javaprojektmusikapp.model.Playlist;
import com.example.javaprojektmusikapp.service.PlaylistService;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class NavigationController
{
    // Referenz auf den MainController für Navigation und View-Wechsel
    private MainController mainController;

    // Navigationsbuttons in der Sidebar
    @FXML
    private Button homeBtn;

    @FXML
    private Button searchNavBtn;

    @FXML
    private Button favoritesBtn;

    // ListView mit allen Playlists in der Sidebar
    @FXML
    private ListView<Playlist> playlistList;

    // Service zur Verwaltung der Playlists
    private PlaylistService playlistService;

    // PseudoClass für den aktuell aktiven Navigationspunkt
    private static final PseudoClass ACTIVE =
            PseudoClass.getPseudoClass("active-item");

    // Wird aufgerufen, wenn die Sidebar geladen wird
    @FXML
    public void initialize()
    {
        // Home ist standardmäßig aktiv
        setActive(homeBtn);
    }

    // Verbindet den NavigationController mit dem MainController
    public void setMainController(MainController mainController)
    {
        this.mainController = mainController;
        this.playlistService = mainController.getPlaylistService();

        // Playlists aus dem Service in die Sidebar laden
        playlistList.getItems().setAll(
                playlistService.getAllPlaylists()
        );

        // Eigene Cell für Playlists inkl. ContextMenu setzen
        playlistList.setCellFactory(list -> new javafx.scene.control.ListCell<>()
        {
            @Override
            protected void updateItem(Playlist playlist, boolean empty)
            {
                super.updateItem(playlist, empty);

                if (empty || playlist == null)
                {
                    setText(null);
                    setContextMenu(null);
                    return;
                }

                setText(playlist.getName());

                // Menüpunkt: Playlist löschen
                MenuItem deleteItem = new MenuItem("Playlist löschen");
                deleteItem.setOnAction(e ->
                {
                    playlistService.deletePlaylist(playlist);

                    // Sidebar neu laden
                    playlistList.getItems().setAll(
                            playlistService.getAllPlaylists()
                    );

                    // Zurück zur Home-Ansicht
                    mainController.showHome();
                });

                // Menüpunkt: Playlist umbenennen
                MenuItem renameItem = new MenuItem("Playlist umbenennen");
                renameItem.setOnAction(e ->
                {
                    TextInputDialog dialog =
                            new TextInputDialog(playlist.getName());

                    dialog.setTitle("Playlist umbenennen");
                    dialog.setHeaderText("Neuer Name der Playlist");
                    dialog.setContentText("Name:");

                    dialog.showAndWait().ifPresent(newName ->
                    {
                        String trimmed = newName.trim();
                        if (trimmed.isEmpty())
                        {
                            return;
                        }

                        playlistService.renamePLaylist(playlist, trimmed);

                        // Sidebar nach Umbenennung aktualisieren
                        playlistList.getItems().setAll(
                                playlistService.getAllPlaylists()
                        );
                    });
                });

                setContextMenu(
                        new javafx.scene.control.ContextMenu(renameItem, deleteItem)
                );
            }
        });

        // Klick auf Playlist dann Playlist-Ansicht anzeigen
        playlistList.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, old, selected) ->
                {
                    if (selected != null)
                    {
                        mainController.showPlaylist(selected);
                    }
                });

        // Einfacher Klick auf Playlist
        playlistList.setOnMouseClicked(event ->
        {
            if (event.getClickCount() == 1)
            {
                Playlist selected =
                        playlistList.getSelectionModel().getSelectedItem();

                if (selected != null)
                {
                    mainController.showPlaylist(selected);
                }
            }
        });
    }

    // Klick auf Home-Button
    @FXML
    private void onHome()
    {
        setActive(homeBtn);
        mainController.showHome();
    }

    // Klick auf Search-Button
    @FXML
    private void onSearch()
    {
        setActive(searchNavBtn);
        mainController.showSearch();
    }

    // Klick auf Favorites-Button
    @FXML
    private void onFavorites()
    {
        setActive(favoritesBtn);
        mainController.showFavorites();
    }

    // Setzt genau einen Navigationsbutton als aktiv
    private void setActive(Button activeBtn)
    {
        homeBtn.pseudoClassStateChanged(ACTIVE, false);
        searchNavBtn.pseudoClassStateChanged(ACTIVE, false);
        favoritesBtn.pseudoClassStateChanged(ACTIVE, false);

        activeBtn.pseudoClassStateChanged(ACTIVE, true);
    }

    // Erstellt eine neue Playlist über Dialog
    @FXML
    private void onCreatePlaylist()
    {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Neue Playlist");
        dialog.setHeaderText("Neue Playlist erstellen");
        dialog.setContentText("Name der Playlist:");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(name ->
        {
            String trimmed = name.trim();
            if (trimmed.isEmpty())
            {
                return;
            }

            Playlist playlist = playlistService.createPlaylist(trimmed);

            if (playlist != null)
            {
                playlistList.getItems().setAll(
                        playlistService.getAllPlaylists()
                );
            }
        });
    }
}
