package com.example.javaprojektmusikapp.service;

import com.example.javaprojektmusikapp.model.Song;
import com.example.javaprojektmusikapp.util.DHL;
import com.example.javaprojektmusikapp.util.DHLIO;
import com.example.javaprojektmusikapp.util.DHLNIO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.ArrayList;

public class FavoritesService
{
    private ArrayList<Song> favorites = new ArrayList<>();
    private DHL fileHandler = new DHLIO();

    // loads the favorites when service starts up
    public FavoritesService()
    {
        loadFavorites();
    }

    // puts song in favorites if its not already there then saves everything
    public void addFavorite(Song song)
    {
        if(!favorites.contains(song))
        {
            favorites.add(song);
            saveFavorites();
            backupFavorites();
        }
    }

    // removes song out of favorites and updates files
    public void removeFavorite(Song song)
    {
        favorites.remove(song);
        saveFavorites();
        backupFavorites();
    }

    // checks if song is already in favorites
    public boolean isFavorite(Song song)
    {
        return favorites.contains(song);
    }

    // adds song if not already in favorites, returns true if added, false if already in favorites
    public boolean toggleFavorite(Song song)
    {
        if(favorites.contains(song))
        {
            removeFavorite(song);
            return false;
        }
        else
        {
            addFavorite(song);
            return true;
        }
    }

    // gives back copy of favorites list so original list isnt changed
    public ArrayList<Song> getFavorites()
    {
        return new ArrayList<>(favorites);
    }

    // writes current favorites to csv file
    private void saveFavorites()
    {
        BufferedWriter writer = fileHandler.schreiben("./data/", "favorites.csv");
        if(writer != null)
        {
            fileHandler.schreibenDateiCSV(favorites, writer);
        }
    }

    // reads favorites from csv when app starts
    public void loadFavorites()
    {
        DHLIO handler = (DHLIO) fileHandler;
        BufferedReader reader = handler.lesen("./data/", "favorites.csv");
        if(reader != null)
        {
            favorites = fileHandler.auslesen(reader);
        }
    }

    // creates serialized backup file
    private void backupFavorites()
    {
        fileHandler.speichernObjekt(favorites, "./data/", "favorites_backup.ser");
    }

    // loads from backup file and rewrites the csv
    @SuppressWarnings("unchecked")
    public void restoreFromBackup()
    {
        Object loaded = fileHandler.ladenObjekt("./data/", "favorites_backup.ser");
        if(loaded instanceof ArrayList<?>)
        {
            favorites = (ArrayList<Song>) loaded;
            saveFavorites(); // Restore to CSV too
        }
    }
}
