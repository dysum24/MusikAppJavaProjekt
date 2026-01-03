package com.example.javaprojektmusikapp.service;

import com.example.javaprojektmusikapp.model.Playlist;
import com.example.javaprojektmusikapp.util.DHL;
import com.example.javaprojektmusikapp.util.DHLIO;

import java.io.BufferedWriter;
import java.util.ArrayList;

public class PlaylistService
{
    private ArrayList<Playlist> playlists = new ArrayList<>();
    private DHL fileHandler = new DHLIO();

    public Playlist createPlaylist(String name)
    {
        if(getPlaylistByName(name) != null)
        {
            return null;
        }
        Playlist playlist = new Playlist(name);
        playlists.add(playlist);
        savePlaylist(playlist);
        return playlist;

    }

    private void savePlaylist(Playlist playlist)
    {
        String filename = "playlist_" + playlist.getName() + ".csv";
        BufferedWriter writer = fileHandler.schreiben("./data/", filename);
        if(writer != null)
        {
            fileHandler.schreibenDateiCSV(playlist.getSongs(), writer);
        }
    }

    public Playlist getPlaylistByName(String name)
    {
        for(Playlist p : playlists)
        {
            if(p.getName().equals(name))
            {
                return p;
            }
        }
        return null;
    }
}
