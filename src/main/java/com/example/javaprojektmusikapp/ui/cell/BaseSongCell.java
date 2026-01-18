package com.example.javaprojektmusikapp.ui.cell;

import com.example.javaprojektmusikapp.controller.MainController;
import com.example.javaprojektmusikapp.model.Song;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BaseSongCell extends ListCell<Song>
{
    // Zeigt den Titel des Songs an
    protected final Label titleLabel = new Label();

    // Zeigt den Künstlernamen des Songs an
    protected final Label artistLabel = new Label();

    // Container für Titel + Künstler (untereinander)
    protected final VBox textBox = new VBox(2);

    // Hauptcontainer der gesamten Song-Zeile
    protected final HBox root = new HBox(12);

    // Referenz auf den MainController
    // Wird benötigt, um Songs an die Playerbar weiterzugeben
    protected final MainController mainController;

    // Konstruktor bekommt den MainController vom aufrufenden Controller
    public BaseSongCell(MainController mainController)
    {
        this.mainController = mainController;

        // CSS-Klassen für einheitliches Song-Design setzen
        titleLabel.getStyleClass().add("song-title");
        artistLabel.getStyleClass().add("song-artist");

        // Titel und Künstler zur VBox hinzufügen
        textBox.getChildren().addAll(titleLabel, artistLabel);

        // VBox in die Root-HBox einfügen
        root.getChildren().add(textBox);

        // Klick auf eine Song-Zeile startet die Wiedergabe
        setOnMouseClicked(e ->
        {
            // Nur linker Mausklick soll reagieren
            if (e.getButton() != MouseButton.PRIMARY)
            {
                return;
            }

            Song song = getItem();
            if (song != null)
            {
                // Übergibt den Song an den MainController
                mainController.showInPlayerbar(song);
            }
        });
    }

    @Override
    protected void updateItem(Song song, boolean empty)
    {
        super.updateItem(song, empty);

        // Leere oder ungültige Zellen korrekt zurücksetzen
        if (empty || song == null)
        {
            setGraphic(null);
            return;
        }

        // Songdaten in die UI-Elemente schreiben
        titleLabel.setText(song.getTrackName());
        artistLabel.setText(song.getArtistName());

        // Zusammengesetzte Root-HBox als Zelleninhalt setzen
        setGraphic(root);
    }
}
