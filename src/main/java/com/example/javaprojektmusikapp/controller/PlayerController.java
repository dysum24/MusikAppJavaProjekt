package com.example.javaprojektmusikapp.controller;

import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PlayerController {

    @FXML
    private Button playPauseBtn;

    private static final PseudoClass PLAYING =
            PseudoClass.getPseudoClass("playing");

    private boolean playing = false;

    @FXML
    private void onPlayPause() {
        playing = !playing;

        playPauseBtn.setText(playing ? "II" : "▶");
        playPauseBtn.pseudoClassStateChanged(PLAYING, playing);
    }
}
