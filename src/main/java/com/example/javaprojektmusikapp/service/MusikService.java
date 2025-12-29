
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

    public MusikService() {
        this.client = HttpClient.newHttpClient();
    }

    public List<Song> searchByTitle(String searchTerm) throws Exception
    {
        String encodedTitle = URLEncoder.encode(searchTerm, StandardCharsets.UTF_8);
        String url = API_URL + "?term=" + encodedTitle + "&media=music&entity=song";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        List<Song> songs = new ArrayList<>();
        JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
        JsonArray results = json.getAsJsonArray("results");

        for (int i = 0; i < results.size(); i++) {
            JsonObject track = results.get(i).getAsJsonObject();
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