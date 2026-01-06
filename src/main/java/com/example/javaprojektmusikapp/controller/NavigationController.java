package com.example.javaprojektmusikapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.css.PseudoClass;

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

    // PseudoClass für den aktuell aktiven Navigationspunkt
    private static final PseudoClass ACTIVE = PseudoClass.getPseudoClass("active-item");

    // Setzt den MainController für Navigation
    public void setMainController(MainController mainController)
    {
        this.mainController = mainController;
    }

    // Wird beim Laden der Sidebar aufgerufen
    // Home ist standardmäßig aktiv
    @FXML
    public void initialize()
    {
        setActive(homeBtn);
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

}
