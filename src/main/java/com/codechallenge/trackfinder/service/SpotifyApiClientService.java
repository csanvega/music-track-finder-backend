package com.codechallenge.trackfinder.service;

import com.codechallenge.trackfinder.dto.spotify.GetAlbumResponse;
import com.codechallenge.trackfinder.dto.spotify.SearchTrackResponse;

public interface SpotifyApiClientService {
    String getToken();
    SearchTrackResponse searchTrack(String isrc);
    GetAlbumResponse getAlbum(String id);
}
