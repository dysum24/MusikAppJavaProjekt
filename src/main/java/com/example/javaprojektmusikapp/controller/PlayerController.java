package com.example.javaprojektmusikapp.controller;

import com.example.javaprojektmusikapp.model.Song;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


public class PlayerController {

    @FXML
    private Button playPauseBtn;

    @FXML
    private Label titleLabel;

    @FXML
    private Label artistLabel;

    @FXML
    private ImageView coverImage;

    private MediaPlayer mediaPlayer;

    @FXML
    private Slider progressSlider;

    @FXML
    private Label currentTimeLabel;

    @FXML
    private Label totalTimeLabel;



    private static final PseudoClass PLAYING =
            PseudoClass.getPseudoClass("playing");

    private boolean playing = false;

    @FXML
    private void initialize() {

        progressSlider.setMin(0);
        progressSlider.setValue(0);
        progressSlider.setDisable(true);


        progressSlider.valueChangingProperty().addListener((obs, wasChanging, isChanging) -> {
            if (!isChanging && mediaPlayer != null) {
                mediaPlayer.seek(javafx.util.Duration.seconds(progressSlider.getValue()));
            }
        });
    }


    @FXML
    private void onPlayPause() {
        if (mediaPlayer == null) {
            return;
        }

        if (playing) {
            mediaPlayer.pause();
            playing = false;
        } else {
            mediaPlayer.play();
            playing = true;
        }

        updatePlayPauseButton();
    }


    public void showSong(Song song) {
        if (song == null) {
            titleLabel.setText("");
            artistLabel.setText("");
            return;
        }

        titleLabel.setText(song.getTrackName());
        artistLabel.setText(song.getArtistName());

        if (song.getArtworkUrl() != null && !song.getArtworkUrl().isBlank()) {
            Image image = new Image(song.getArtworkUrl(), true);
            coverImage.setImage(image);
        } else {
            coverImage.setImage(null);
        }
        playPreview(song);
    }

    private void playPreview(Song song) {
        stopPlayer();

        if (song.getPreviewUrl() == null || song.getPreviewUrl().isBlank()) {
            return;
        }

        try {
            Media media = new Media(song.getPreviewUrl());
            mediaPlayer = new MediaPlayer(media);

            mediaPlayer.setOnReady(() -> {
                double duration = mediaPlayer.getMedia().getDuration().toSeconds();
                progressSlider.setMax(duration);
                progressSlider.setValue(0);
                progressSlider.setDisable(false);

                totalTimeLabel.setText(formatTime(duration));
                currentTimeLabel.setText("0:00");
            });

            mediaPlayer.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
                if (!progressSlider.isValueChanging()) {
                    progressSlider.setValue(newTime.toSeconds());
                }

                currentTimeLabel.setText(formatTime(newTime.toSeconds()));
            });

            mediaPlayer.setOnEndOfMedia(() -> {
                playing = false;
                updatePlayPauseButton();
                progressSlider.setValue(progressSlider.getMax());
            });

            mediaPlayer.play();
            playing = true;
            updatePlayPauseButton();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stopPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
            mediaPlayer = null;
        }
        playing = false;
        updatePlayPauseButton();

        if (progressSlider != null) {
            progressSlider.setValue(0);
            progressSlider.setDisable(true);
        }
        currentTimeLabel.setText("0:00");
        totalTimeLabel.setText("0:00");
    }

    private void updatePlayPauseButton() {
        playPauseBtn.setText(playing ? "II" : "▶");
        playPauseBtn.pseudoClassStateChanged(PLAYING, playing);
    }

    private String formatTime(double seconds) {
        int s = (int) seconds;
        int min = s / 60;
        int sec = s % 60;
        return min + ":" + String.format("%02d", sec);
    }
}
