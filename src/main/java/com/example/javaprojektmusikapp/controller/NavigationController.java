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
import com.example.javaprojektmusikapp.service.PlaylistService;
import com.example.javaprojektmusikapp.model.Playlist;



// Steuert die Sidebar-Navigation (Home, Search, Favorites).
// Sorgt dafür, dass immer genau ein Button aktiv ist.
public class NavigationController {

    // Verbindet die Sidebar mit dem MainController
    private MainController mainController;

    // Buttons aus der Sidebar (sidebar.fxml)
    @FXML
    private Button homeBtn;

    @FXML
    private Button searchNavBtn;

    @FXML
    private Button favoritesBtn;

    @FXML
    private ListView<Playlist> playlistList;

    private PlaylistService playlistService;


    // PseudoClass für den aktuell aktiven Navigationspunkt
    private static final PseudoClass ACTIVE = PseudoClass.getPseudoClass("active-item");

    // Wird beim Laden der Sidebar aufgerufen
    // Home ist standardmäßig aktiv
    @FXML
    public void initialize()
    {
        setActive(homeBtn);
    }

    public void setMainController(MainController mainController)
    {
        this.mainController = mainController;
        this.playlistService = mainController.getPlaylistService();

        //  Playlists in Sidebar laden
        playlistList.getItems().setAll(
                playlistService.getAllPlaylists()
        );

        //  Rechtsklick-Menü für Playlists (LÖSCHEN)
        playlistList.setCellFactory(list -> new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(Playlist playlist, boolean empty) {
                super.updateItem(playlist, empty);

                if (empty || playlist == null) {
                    setText(null);
                    setContextMenu(null);
                    return;
                }

                setText(playlist.getName());

                MenuItem deleteItem = new MenuItem("Playlist löschen");
                deleteItem.setOnAction(e -> {

                    playlistService.deletePlaylist(playlist);

                    playlistList.getItems().setAll(
                            playlistService.getAllPlaylists()
                    );

                    mainController.showHome();
                });

// ️ NEU: Playlist umbenennen
                MenuItem renameItem = new MenuItem("Playlist umbenennen");
                renameItem.setOnAction(e -> {

                    TextInputDialog dialog =
                            new TextInputDialog(playlist.getName());

                    dialog.setTitle("Playlist umbenennen");
                    dialog.setHeaderText("Neuer Name der Playlist");
                    dialog.setContentText("Name:");

                    dialog.showAndWait().ifPresent(newName -> {

                        String trimmed = newName.trim();
                        if (trimmed.isEmpty()) {
                            return;
                        }

                        // vorhandene Backend-Methode nutzen (kein Backend-Ändern!)
                        playlistService.renamePLaylist(playlist, trimmed);

                        // Sidebar aktualisieren
                        playlistList.getItems().setAll(
                                playlistService.getAllPlaylists()
                        );
                    });
                });

//  WICHTIG: BEIDE MenuItems ins ContextMenu
                setContextMenu(
                        new javafx.scene.control.ContextMenu(renameItem, deleteItem)
                );

            }
        });

        // 3️ Playlist auswählen → anzeigen
        playlistList.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, old, selected) -> {
                    if (selected != null) {
                        mainController.showPlaylist(selected);
                    }
                });
    }

    // Klick auf "Home"
    @FXML
    private void onHome()
    {
        setActive(homeBtn);
        mainController.showHome();
    }

    // Klick auf "Search"
    @FXML
    private void onSearch()
    {
        setActive(searchNavBtn);
        mainController.showSearch();
    }

    // Klick auf "Favorites"
    @FXML
    private void onFavorites()
    {
        setActive(favoritesBtn);
        mainController.showFavorites();
    }

    // Setzt genau einen Button als aktiv
    private void setActive(Button activeBtn)
    {
        homeBtn.pseudoClassStateChanged(ACTIVE, false);
        searchNavBtn.pseudoClassStateChanged(ACTIVE, false);
        favoritesBtn.pseudoClassStateChanged(ACTIVE, false);

        activeBtn.pseudoClassStateChanged(ACTIVE, true);
    }

    @FXML
    private void onCreatePlaylist() {

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Neue Playlist");
        dialog.setHeaderText("Neue Playlist erstellen");
        dialog.setContentText("Name der Playlist:");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(name -> {

            String trimmed = name.trim();
            if (trimmed.isEmpty()) {
                return;
            }

            Playlist playlist = playlistService.createPlaylist(trimmed);

            if (playlist != null) {
                playlistList.getItems().setAll(
                        playlistService.getAllPlaylists()
                );
            }
        });
    }


}
