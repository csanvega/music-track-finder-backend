package com.codechallenge.trackfinder.service;

import com.codechallenge.trackfinder.dto.SpotifySearchTrackResponse;

public interface SpotifyApiClientService {
    String getToken();
    SpotifySearchTrackResponse searchTrack(String isrc);
}
