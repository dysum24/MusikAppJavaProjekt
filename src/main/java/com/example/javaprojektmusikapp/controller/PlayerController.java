package com.example.javaprojektmusikapp.controller;

import com.example.javaprojektmusikapp.model.Song;
import com.example.javaprojektmusikapp.service.PlayerService;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaPlayer;

public class PlayerController
{
    // Play / Pause Button in der Playerbar
    @FXML
    private Button playPauseBtn;

    // Label für den Songtitel
    @FXML
    private Label titleLabel;

    // Label für den Künstlernamen
    @FXML
    private Label artistLabel;

    // Coverbild des aktuellen Songs
    @FXML
    private ImageView coverImage;

    // Slider für den aktuellen Abspiel-Fortschritt
    @FXML
    private Slider progressSlider;

    // Anzeige der aktuellen Abspielzeit
    @FXML
    private Label currentTimeLabel;

    // Anzeige der Gesamtdauer des Songs
    @FXML
    private Label totalTimeLabel;

    // Slider für die Lautstärke
    @FXML
    private Slider volumeSlider;

    // Service, der den MediaPlayer kapselt
    private PlayerService playerService = new PlayerService();

    // PseudoClass für den Playing-Zustand des Buttons
    private static final PseudoClass PLAYING =
            PseudoClass.getPseudoClass("playing");

    @FXML
    private void initialize()
    {
        // Progress-Slider initial zurücksetzen und deaktivieren
        progressSlider.setMin(0);
        progressSlider.setValue(0);
        progressSlider.setDisable(true);

        // Springt im Song, wenn der Nutzer den Slider loslässt
        progressSlider.valueChangingProperty().addListener((obs, wasChanging, isChanging) ->
        {
            if (!isChanging && playerService.getMediaPlayer() != null)
            {
                playerService.getMediaPlayer().seek(
                        javafx.util.Duration.seconds(progressSlider.getValue())
                );
            }
        });

        // Lautstärke-Slider initialisieren (0–100)
        volumeSlider.setMin(0);
        volumeSlider.setMax(100);
        volumeSlider.setValue(70);

        // Lautstärke live an den PlayerService weitergeben
        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) ->
        {
            playerService.setVolume(newVal.doubleValue() / 100.0);
        });
    }

    // Play / Pause-Button gedrückt
    @FXML
    private void onPlayPause()
    {
        // Kein Player vorhanden dann nichts tun
        if (playerService.getMediaPlayer() == null)
        {
            return;
        }

        // Wiedergabe pausieren oder fortsetzen
        if (playerService.isPlaying())
        {
            playerService.pause();
        }
        else
        {
            playerService.resume();
        }

        updatePlayPauseButton();
    }

    // Zeigt einen neuen Song in der Playerbar an
    public void showSong(Song song)
    {
        // Kein Song dann Anzeige zurücksetzen
        if (song == null)
        {
            titleLabel.setText("");
            artistLabel.setText("");
            return;
        }

        // Songinformationen setzen
        titleLabel.setText(song.getTrackName());
        artistLabel.setText(song.getArtistName());

        // Coverbild laden, falls vorhanden
        if (song.getArtworkUrl() != null && !song.getArtworkUrl().isBlank())
        {
            Image image = new Image(song.getArtworkUrl(), true);
            coverImage.setImage(image);
        }
        else
        {
            coverImage.setImage(null);
        }

        // Vorschau abspielen
        playPreview(song);
    }

    // Startet die 30-Sekunden-Preview des Songs
    private void playPreview(Song song)
    {
        // Aktuelle Wiedergabe stoppen
        stopPlayer();

        // Kein Preview vorhanden dann abbrechen
        if (song.getPreviewUrl() == null || song.getPreviewUrl().isBlank())
        {
            return;
        }

        // Song über den PlayerService starten
        playerService.play(song);

        // Aktuelle Lautstärke setzen
        playerService.setVolume(volumeSlider.getValue() / 100.0);

        MediaPlayer mediaPlayer = playerService.getMediaPlayer();
        if (mediaPlayer == null)
        {
            return;
        }

        // Wird ausgeführt, sobald der Song geladen ist
        mediaPlayer.setOnReady(() ->
        {
            double duration =
                    mediaPlayer.getMedia().getDuration().toSeconds();

            // Progress-Slider vorbereiten
            progressSlider.setMax(duration);
            progressSlider.setValue(0);
            progressSlider.setDisable(false);

            totalTimeLabel.setText(formatTime(duration));
            currentTimeLabel.setText("0:00");
        });

        // Aktualisiert Slider und Zeit während der Wiedergabe
        mediaPlayer.currentTimeProperty().addListener((obs, oldTime, newTime) ->
        {
            if (!progressSlider.isValueChanging())
            {
                progressSlider.setValue(newTime.toSeconds());
            }

            currentTimeLabel.setText(
                    formatTime(newTime.toSeconds())
            );
        });

        // Wird aufgerufen, wenn der Song zu Ende ist
        mediaPlayer.setOnEndOfMedia(() ->
        {
            progressSlider.setValue(progressSlider.getMax());
            updatePlayPauseButton();
        });

        updatePlayPauseButton();
    }

    // Stoppt die Wiedergabe und setzt die Anzeige zurück
    private void stopPlayer()
    {
        playerService.stop();
        updatePlayPauseButton();

        if (progressSlider != null)
        {
            progressSlider.setValue(0);
            progressSlider.setDisable(true);
        }

        currentTimeLabel.setText("0:00");
        totalTimeLabel.setText("0:00");
    }

    // Aktualisiert Text und Style des Play/Pause-Buttons
    private void updatePlayPauseButton()
    {
        boolean playing = playerService.isPlaying();
        playPauseBtn.setText(playing ? "II" : "▶");
        playPauseBtn.pseudoClassStateChanged(PLAYING, playing);
    }

    // Formatiert Sekunden in mm:ss
    private String formatTime(double seconds)
    {
        int s = (int) seconds;
        int min = s / 60;
        int sec = s % 60;
        return min + ":" + String.format("%02d", sec);
    }
}
