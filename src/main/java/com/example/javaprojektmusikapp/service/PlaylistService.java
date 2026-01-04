package com.example.javaprojektmusikapp.service;

import com.example.javaprojektmusikapp.model.Playlist;
import com.example.javaprojektmusikapp.model.Song;
import com.example.javaprojektmusikapp.util.DHL;
import com.example.javaprojektmusikapp.util.DHLIO;

import java.io.BufferedWriter;
import java.io.File;
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

    public void addSongToPlaylist(Playlist playlist, Song song)
    {
        playlist.addSong(song);
        savePlaylist(playlist);
    }

    public void removeSongFromPlaylist(Playlist playlist, Song song)
    {
        playlist.removeSong(song);
        savePlaylist(playlist);
    }

    public void renamePLaylist(Playlist playlist, String newName)
    {
        File oldFile = new File("./data/playlist_" + playlist.getName() + ".csv");
        oldFile.delete();

        playlist.setName(newName);
        savePlaylist(playlist);
    }

    public void deletePlaylist(Playlist playlist)
    {
        playlists.remove(playlist);
        File file = new File("./data/playlist_" + playlist.getName() + ".csv");
        if(file.exists())
        {
           file.delete();
        }

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
