package com.example.javaprojektmusikapp.service;

import com.example.javaprojektmusikapp.model.Playlist;
import com.example.javaprojektmusikapp.model.Song;
import com.example.javaprojektmusikapp.util.DHL;
import com.example.javaprojektmusikapp.util.DHLIO;

import java.io.BufferedReader;
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
    private void loadPlaylist()
    {
        File folder = new File("./data");
        if(!folder.exists())
        {
            folder.mkdirs();
            return;
        }

        File[] files = folder.listFiles((dir, name) -> name.startsWith("playlist_") && name.endsWith(".csv"));

        if(files != null)
        {
            for(File file : files)
            {
                String playlistName = file.getName().replace("playlist_", "").replace(".csv", "");

                DHLIO handler = (DHLIO) fileHandler;
                BufferedReader reader = handler.lesen("./data/", file.getName());

                if(reader != null)
                {
                    ArrayList<Song> songs = handler.auslesen(reader);

                    Playlist playlist = new Playlist(playlistName);
                    for(Song song: songs)
                    {
                        playlist.addSong(song);
                    }
                    playlists.add(playlist);
                }
            }
        }
        System.out.println("Playlists geladen:" + playlists.size());
    }

    public ArrayList<Playlist> getAllPlaylists()
    {
        return playlists;
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
