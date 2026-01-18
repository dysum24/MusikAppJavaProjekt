package com.example.javaprojektmusikapp.controller;

import javafx.fxml.FXML;

public class HomeController
{
    // Referenz auf den MainController für Navigation
    private MainController mainController;

    // Wird vom MainController gesetzt, nachdem die View geladen wurde
    public void setMainController(MainController mainController)
    {
        // MainController speichern
        this.mainController = mainController;
    }

    // Öffnet die Genre-Ansicht für Pop
    @FXML
    private void onPop()
    {
        mainController.showGenre("Pop");
    }

    // Öffnet die Genre-Ansicht für Rock
    @FXML
    private void onRock()
    {
        mainController.showGenre("Rock");
    }

    // Öffnet die Genre-Ansicht für Hip-Hop
    @FXML
    private void onHipHop()
    {
        mainController.showGenre("Hip-Hop");
    }

    // Öffnet die Genre-Ansicht für Electronic
    @FXML
    private void onElectronic()
    {
        mainController.showGenre("Electronic");
    }

    // Öffnet die Genre-Ansicht für Jazz
    @FXML
    private void onJazz()
    {
        mainController.showGenre("Jazz");
    }

    // Öffnet die Genre-Ansicht für Classical
    @FXML
    private void onClassical()
    {
        mainController.showGenre("Classical");
    }
}
