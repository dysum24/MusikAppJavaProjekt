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

    public void addFavorite(Song song)
    {
        if(!favorites.contains(song))
        {
            favorites.add(song);
            saveFavorites();
        }
    }

    public void removeFavorite(Song song)
    {
        favorites.remove(song);
        saveFavorites();
    }

    public boolean isFavorite(Song song)
    {
        return favorites.contains(song);
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
}
