package com.example.javaprojektmusikapp.service;

import com.example.javaprojektmusikapp.model.Song;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class PlayerService
{
    private MediaPlayer mediaPlayer;
    private Song currentSong;
    private boolean isPlaying = false;


    public void play(Song song)
    {

        if(mediaPlayer != null)
        {
            mediaPlayer.stop();
        }


        String previewUrl = song.getPreviewUrl();
        if(previewUrl == null || previewUrl.isEmpty())
        {
            System.out.println("Kein Preview verfügbar für: " + song.getTrackName());
            return;
        }

        try
        {
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

    public void pause()
    {
        if(mediaPlayer != null && isPlaying)
        {
            mediaPlayer.pause();
            isPlaying = false;
        }
    }

    public void resume()
    {
        if(mediaPlayer != null && !isPlaying)
        {
            mediaPlayer.play();
            isPlaying = true;
        }
    }

    public void stop()
    {
        if(mediaPlayer != null)
        {
            mediaPlayer.stop();
            isPlaying = false;
            currentSong = null;
        }
    }

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