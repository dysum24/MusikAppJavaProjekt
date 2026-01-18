package com.example.javaprojektmusikapp.service;

import com.example.javaprojektmusikapp.model.Song;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class PlayerService
{
    private MediaPlayer mediaPlayer;
    private Song currentSong;
    private boolean isPlaying = false;

    // starts play song, stops what was playing before
    public void play(Song song)
    {
        // kills the old player if somethings already running
        if(mediaPlayer != null)
        {
            mediaPlayer.stop();
        }

        // check if song actually has a preview URL
        String previewUrl = song.getPreviewUrl();
        if(previewUrl == null || previewUrl.isEmpty())
        {
            System.out.println("Kein Preview verfügbar für: " + song.getTrackName());
            return;
        }

        try
        {
            //create new media player and start playback
            Media media = new Media(previewUrl);
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
            currentSong = song;
            isPlaying = true;
            System.out.println("Spielt: " + song.getTrackName());
        }
        catch(Exception e)
        {
            System.out.println("Fehler beim Abspielen: " + e.getMessage());
        }
    }

    // pauses current song if somethings playing
    public void pause()
    {
        if(mediaPlayer != null && isPlaying)
        {
            mediaPlayer.pause();
            isPlaying = false;
        }
    }

    // continues playing after pause
    public void resume()
    {
        if(mediaPlayer != null && !isPlaying)
        {
            mediaPlayer.play();
            isPlaying = true;
        }
    }

    //stops playback and clears current song
    public void stop()
    {
        if(mediaPlayer != null)
        {
            mediaPlayer.stop();
            isPlaying = false;
            currentSong = null;
        }
    }

    // adjusts volume, expects value between 0.0 und 1.0
    public void setVolume(double volume)
    {
        if(mediaPlayer != null)
        {
            mediaPlayer.setVolume(volume);
        }
    }

    public Song getCurrentSong()
    {
        return currentSong;
    }

    public boolean isPlaying()
    {
        return isPlaying;
    }

    public MediaPlayer getMediaPlayer()
    {
        return mediaPlayer;
    }
}