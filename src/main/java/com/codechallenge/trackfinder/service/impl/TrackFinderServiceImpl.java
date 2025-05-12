package com.codechallenge.trackfinder.service.impl;

import com.codechallenge.trackfinder.dto.TrackDetailsResponse;
import com.codechallenge.trackfinder.service.SpotifyApiAuthService;
import com.codechallenge.trackfinder.service.TrackFinderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrackFinderServiceImpl implements TrackFinderService {

    private final SpotifyApiAuthService spotifyApiAuthService;

    public TrackDetailsResponse createTrack(String isrc) {
        String token = spotifyApiAuthService.getToken();
        return TrackDetailsResponse.builder()
                .name("Song")
                .albumName(token)
                .playbackSeconds(3000)
                .artistName("Kanye West")
                .isExplicit(true)
                .build();
    }
}
