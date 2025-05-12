package com.codechallenge.trackfinder.service.impl;

import com.codechallenge.trackfinder.dto.SpotifySearchTrackResponse;
import com.codechallenge.trackfinder.dto.SpotifyTrackItem;
import com.codechallenge.trackfinder.dto.TrackDetailsResponse;
import com.codechallenge.trackfinder.service.SpotifyApiClientService;
import com.codechallenge.trackfinder.service.TrackFinderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrackFinderServiceImpl implements TrackFinderService {

    private final SpotifyApiClientService spotifyApiClientService;

    public TrackDetailsResponse createTrack(String isrc) {
        try {
            SpotifySearchTrackResponse responseSearch = spotifyApiClientService.searchTrack(isrc);

            if (responseSearch == null
                    || responseSearch.tracks() == null
                    || responseSearch.tracks().items() == null
                    || responseSearch.tracks().items().isEmpty()) {
                throw new Exception("No se encontró track con ISRC: " + isrc);
            }

            SpotifyTrackItem trackItem = responseSearch.tracks().items().get(0);

            return TrackDetailsResponse.builder()
                    .name(trackItem.name())
                    .albumName("album")
                    .playbackSeconds(trackItem.duration_ms())
                    .artistName("Kanye West")
                    .isExplicit(trackItem.explicit())
                    .build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
