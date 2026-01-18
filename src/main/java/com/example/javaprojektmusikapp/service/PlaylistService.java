package com.example.javaprojektmusikapp.service;

import com.example.javaprojektmusikapp.model.Playlist;
import com.example.javaprojektmusikapp.model.Song;
import com.example.javaprojektmusikapp.util.DHL;
import com.example.javaprojektmusikapp.util.DHLIO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class PlaylistService implements Serializable
{
    private static final long serialVersionUID = 1L;

    private ArrayList<Playlist> playlists = new ArrayList<>();
    private DHL fileHandler = new DHLIO();

    // loads playlists when service starts up
    public PlaylistService()
    {
        loadPlaylist();
    }

    // creates new playlist with given name, reeturns null if name already exists
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

    // adds song to playlist and saves changes
    public void addSongToPlaylist(Playlist playlist, Song song)
    {
        playlist.addSong(song);
        savePlaylist(playlist);
    }

    // removes song from playlist and updates files
    public void removeSongFromPlaylist(Playlist playlist, Song song)
    {
        playlist.removeSong(song);
        savePlaylist(playlist);
    }

    // renames playlist by deleting old file and creating new one
    public void renamePLaylist(Playlist playlist, String newName)
    {
        File oldFile = new File("./data/playlist_" + playlist.getName() + ".csv");
        oldFile.delete();

        playlist.setName(newName);
        savePlaylist(playlist);
    }

    //deletes playlist from list and removes its file
    public void deletePlaylist(Playlist playlist)
    {
        playlists.remove(playlist);
        File file = new File("./data/playlist_" + playlist.getName() + ".csv");
        if(file.exists())
        {
           file.delete();
        }

    }

    // saves playlist songs to csv file name after playlist name
    private void savePlaylist(Playlist playlist)
    {
        String filename = "playlist_" + playlist.getName() + ".csv";
        BufferedWriter writer = fileHandler.schreiben("./data/", filename);
        if(writer != null)
        {
            fileHandler.schreibenDateiCSV(playlist.getSongs(), writer);
        }
    }

    // scans data folder for playlist files and loads them all
    public void loadPlaylist()
    {
        File folder = new File("./data");
        if(!folder.exists())
        {
            folder.mkdirs();
            return;
        }
        // only grab files that start with "playlist_" and end with ".csv"
        File[] files = folder.listFiles((dir, name) -> name.startsWith("playlist_") && name.endsWith(".csv"));

        if(files != null)
        {
            for(File file : files)
            {
                // extract playlist name from filename
                String playlistName = file.getName().replace("playlist_", "").replace(".csv", "");

                DHLIO handler = (DHLIO) fileHandler;
                BufferedReader reader = handler.lesen("./data/", file.getName());

                if(reader != null)
                {
                    // read all songs from file and rebuild playlist
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

    // returns all playlists
    public ArrayList<Playlist> getAllPlaylists()
    {
        return playlists;
    }

    // fines playlist by exact name match, returns null if not found
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
