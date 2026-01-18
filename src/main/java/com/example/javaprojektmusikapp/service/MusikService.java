
package com.example.javaprojektmusikapp.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.example.javaprojektmusikapp.model.Song;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MusikService {
    private static final String API_URL = "https://itunes.apple.com/search";
    private final HttpClient client;

    // sets up the http client for making api calls
    public MusikService() {
        this.client = HttpClient.newHttpClient();
    }

    // searches itunes api for songs matching the search term
    public List<Song> searchByTitle(String searchTerm) throws Exception
    {
        // encodes the search term so special characters are handled correctly
        String encodedTitle = URLEncoder.encode(searchTerm, StandardCharsets.UTF_8);
        String url = API_URL + "?term=" + encodedTitle + "&media=music&entity=song";

        // build and send the get request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // parse json response and gets song data
        List<Song> songs = new ArrayList<>();
        JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
        JsonArray results = json.getAsJsonArray("results");

        // loop through the results and create song objects
        for (int i = 0; i < results.size(); i++) {
            JsonObject track = results.get(i).getAsJsonObject();
            // use has() to check if fields exist before getting them, fallsback to defaults
            Song song = new Song(
                    String.valueOf(track.get("trackId").getAsInt()),
                    track.get("trackName").getAsString(),
                    track.get("artistName").getAsString(),
                    track.has("collectionName") ? track.get("collectionName").getAsString() : "",
                    track.has("artworkUrl100") ? track.get("artworkUrl100").getAsString() : "",
                    track.has("trackTimeMillis") ? track.get("trackTimeMillis").getAsInt() : 0,
                    track.has("previewUrl") ? track.get("previewUrl").getAsString() : "",
                    track.has("trackPrice") ? track.get("trackPrice").getAsDouble() : 0.0,
                    track.has("releaseDate") ? track.get("releaseDate").getAsString() : ""
            );
            songs.add(song);
        }

        return songs;
    }
}