package com.example.javaprojektmusikapp.controller;
import com.example.javaprojektmusikapp.model.Song;
import com.example.javaprojektmusikapp.service.MusikService;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class SearchController {

    private MainController mainController;

    @FXML
    private TextField searchField;

    @FXML
    private ListView<Song> resultsList;

    private final MusikService musikService = new MusikService();

    @FXML
    private void initialize()
    {
        resultsList.setVisible(false);
        resultsList.setManaged(false);
        resultsList.getItems().clear();

        resultsList.setCellFactory(list -> new ListCell<>()
        {
            @Override
            protected void updateItem(Song song, boolean empty)
            {
                super.updateItem(song, empty);

                if(empty || song == null)
                {
                    setText(null);
                    return;
                }
                setText(song.getTrackName() + "\n" + song.getArtistName());
            }
        });

        resultsList.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, old, selected) -> {
                    if (selected != null && mainController != null) {
                        mainController.showInPlayerbar(selected);
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
    }

}
