package com.example.javaprojektmusikapp.ui.cell;

import com.example.javaprojektmusikapp.controller.MainController;

public class GenreSongCell extends BaseSongCell
{
    // Eigene SongCell für die Genre-Ansicht
    // Erbt komplett das Verhalten von BaseSongCell
    // (Anzeige + Klick startet Player)
    public GenreSongCell(MainController mainController)
    {
        // Übergibt den MainController an die Basisklasse
        super(mainController);
    }
}
