package com.example.javaprojektmusikapp.model;

import java.util.ArrayList;

public class Playlist
{
    private String name;
    private ArrayList<Song> songs;

    public Playlist(String name)
    {
        this.name = name;
        this.songs = new ArrayList<>();
    }

    public void addSong(Song song)
    {
        if(!songs.contains(song))
        {
            songs.add(song);
        }
    }

    public void removeSong(Song song)
    {
        songs.remove(song);
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize()
    {
        return songs.size();
    }

    @Override
    public String toString()
    {
        return name + " (" + songs.size() + "Lieder)";
    }

   @Override
   public boolean equals(Object obj)
   {
       return obj instanceof Playlist && ((Playlist)obj).getName().equals(name);
   }

   @Override
    public int hashCode()
   {
       return name.hashCode();
       //song contians
   }

}
