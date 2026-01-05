package com.example.javaprojektmusikapp.model;

public class Song
{
    private String trackId;
    private String trackName;
    private String artistName;
    private String albumName;
    private String artworkUrl;
    private int trackTimeMillis;
    private String previewUrl;
    private double trackPrice;
    private String releaseDate;

    public Song(String trackId, String trackName, String artistName, String albumName, String artworkUrl, int trackTimeMillis, String previewUrl, double trackPrice, String releaseDate)
    {
        this.trackId = trackId;
        this.trackName = trackName;
        this.artistName = artistName;
        this.albumName = albumName;
        this.artworkUrl = artworkUrl;
        this.trackTimeMillis = trackTimeMillis;
        this.previewUrl = previewUrl;
        this.trackPrice = trackPrice;
        this.releaseDate = releaseDate;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getArtworkUrl() {
        return artworkUrl;
    }

    public void setArtworkUrl(String artworkUrl) {
        this.artworkUrl = artworkUrl;
    }

    public int getTrackTimeMillis() {
        return trackTimeMillis;
    }

    public void setTrackTimeMillis(int trackTimeMillis) {
        this.trackTimeMillis = trackTimeMillis;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public double getTrackPrice() {
        return trackPrice;
    }

    public void setTrackPrice(double trackPrice) {
        this.trackPrice = trackPrice;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof Song && ((Song)obj).getTrackId().equals(trackId);
    }

    @Override
    public int hashCode()
    {
        return trackId.hashCode();
    }
}
