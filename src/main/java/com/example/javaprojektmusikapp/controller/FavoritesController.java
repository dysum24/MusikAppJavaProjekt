package com.example.javaprojektmusikapp.controller;

import com.example.javaprojektmusikapp.model.Song;
import com.example.javaprojektmusikapp.service.FavoritesService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;



public class FavoritesController {

    @FXML
    private ListView<Song> favoritesList;

    private final FavoritesService favoritesService = new FavoritesService();

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void initialize() {

        // Favoriten aus dem Backend laden
        favoritesList.getItems().setAll(favoritesService.getFavorites());

        // Anzeige der Songs
        favoritesList.setCellFactory(list -> new ListCell<>() {

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
                    return;
                }

                titleLabel.setText(song.getTrackName());
                artistLabel.setText(song.getArtistName());

                if (song.getArtworkUrl() != null && !song.getArtworkUrl().isBlank()) {
                    coverImage.setImage(new Image(song.getArtworkUrl(), true));
                } else {
                    coverImage.setImage(null);
                }

                heartButton.setText("♥");
                heartButton.getStyleClass().setAll("heart-button", "favorite");


                heartButton.setOnAction(e -> {
                    favoritesService.removeFavorite(song);
                    favoritesList.getItems().remove(song);
                    e.consume();
                });

                setText(null);
                setGraphic(root);
            }
        });

        favoritesList.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, old, selected) -> {
                    if (selected != null && mainController != null) {
                        mainController.showInPlayerbar(selected);
                    }
                });
    }
}
