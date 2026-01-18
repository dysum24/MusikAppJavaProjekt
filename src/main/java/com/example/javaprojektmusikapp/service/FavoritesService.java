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

    public FavoritesService()
    {
        loadFavorites();
    }

    public void addFavorite(Song song)
    {
        if(!favorites.contains(song))
        {
            favorites.add(song);
            saveFavorites();
            backupFavorites();
        }
    }

    public void removeFavorite(Song song)
    {
        favorites.remove(song);
        saveFavorites();
        backupFavorites();
    }

    public boolean isFavorite(Song song)
    {
        return favorites.contains(song);
    }

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

    public ArrayList<Song> getFavorites()
    {
        return new ArrayList<>(favorites);
    }

    private void saveFavorites()
    {
        BufferedWriter writer = fileHandler.schreiben("./data/", "favorites.csv");
        if(writer != null)
        {
            fileHandler.schreibenDateiCSV(favorites, writer);
        }
    }

    public void loadFavorites()
    {
        DHLIO handler = (DHLIO) fileHandler;
        BufferedReader reader = handler.lesen("./data/", "favorites.csv");
        if(reader != null)
        {
            favorites = fileHandler.auslesen(reader);
        }
    }

    private void backupFavorites()
    {
        fileHandler.speichernObjekt(favorites, "./data/", "favorites_backup.ser");
    }

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
