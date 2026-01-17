package com.example.javaprojektmusikapp.controller;

import com.example.javaprojektmusikapp.model.Song;
import com.example.javaprojektmusikapp.service.MusikService;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import com.example.javaprojektmusikapp.service.FavoritesService;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ChoiceDialog;
import com.example.javaprojektmusikapp.service.PlaylistService;
import com.example.javaprojektmusikapp.model.Playlist;
import java.util.List;
import java.util.Optional;

public class SearchController {

    private MainController mainController;

    @FXML
    private TextField searchField;

    @FXML
    private ListView<Song> resultsList;

    private final MusikService musikService = new MusikService();

    private final FavoritesService favoritesService = new FavoritesService();

    private PlaylistService playlistService;


    @FXML
    private void initialize()
    {
        resultsList.setVisible(false);
        resultsList.setManaged(false);
        resultsList.getItems().clear();

        resultsList.setCellFactory(list -> new ListCell<Song>() {

            private final ImageView coverImage = new ImageView();
            private final Label titleLabel = new Label();
            private final Label artistLabel = new Label();
            private final Button heartButton = new Button();

            private final VBox textBox = new VBox(2);
            private final Region spacer = new Region();
            private final HBox root = new HBox(12);

            {
                coverImage.setFitWidth(40);
                coverImage.setFitHeight(40);
                coverImage.setPreserveRatio(true);

                textBox.getChildren().addAll(titleLabel, artistLabel);
                HBox.setHgrow(spacer, Priority.ALWAYS);
                root.getChildren().addAll(coverImage, textBox, spacer, heartButton);
            }

            @Override
            protected void updateItem(Song song, boolean empty) {
                super.updateItem(song, empty);

                if (empty || song == null) {
                    setGraphic(null);
                    setContextMenu(null);
                    return;
                }

                titleLabel.setText(song.getTrackName());
                artistLabel.setText(song.getArtistName());

                updateHeart(song);

                heartButton.setOnAction(e -> {
                    favoritesService.toggleFavorite(song);
                    updateHeart(song);
                    e.consume();
                });

                ContextMenu contextMenu = new ContextMenu();
                MenuItem addToPlaylistItem = new MenuItem("Zu Playlist hinzufügen…");

                addToPlaylistItem.setOnAction(e -> {

                    List<Playlist> playlists = playlistService.getAllPlaylists();
                    if (playlists.isEmpty()) return;

                    ChoiceDialog<Playlist> dialog =
                            new ChoiceDialog<>(playlists.get(0), playlists);

                    Optional<Playlist> result = dialog.showAndWait();

                    result.ifPresent(playlist -> {

                        if (playlist.getSongs().contains(song)) {
                            return;
                        }

                        playlistService.addSongToPlaylist(playlist, song);

                        if (mainController != null) {
                            mainController.refreshCurrentPlaylist();
                        }
                    });
                });

                contextMenu.getItems().add(addToPlaylistItem);
                setContextMenu(contextMenu);

                setGraphic(root);
            }

            private void updateHeart(Song song) {
                heartButton.getStyleClass().setAll("heart-button");

                if (favoritesService.isFavorite(song)) {
                    heartButton.setText("♥");
                    heartButton.getStyleClass().add("favorite");
                } else {
                    heartButton.setText("♡");
                }
            }
        });



        resultsList.setOnMouseClicked(event -> {
            if (event.getButton() == javafx.scene.input.MouseButton.PRIMARY) {
                Song selected = resultsList.getSelectionModel().getSelectedItem();
                if (selected != null && mainController != null) {
                    mainController.showInPlayerbar(selected);
                }
            }
        });

        // Live-Suche
        searchField.textProperty().addListener((obs, o, n) -> onSearch());
    }

    @FXML
    private void onSearch() {

        String query = searchField.getText();
        resultsList.getItems().clear();

        if(query == null || query.isBlank())
        {
            resultsList.setVisible(false);
            resultsList.setManaged(false);
            return;
        }

        try{
            resultsList.getItems().setAll(musikService.searchByTitle(query));

            resultsList.setVisible(true);
            resultsList.setManaged(true);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
        this.playlistService = mainController.getPlaylistService();
    }


}
