package com.codechallenge.trackfinder.service.impl;

import com.codechallenge.trackfinder.dto.*;
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
                throw new Exception("ISRC was not found: " + isrc);
            }

            SpotifyTrackItem trackItem = responseSearch.tracks().items().get(0);
            SpotifyArtist artist = trackItem.artists().get(0);
            String albumId = trackItem.album().id();

            SpotifyGetAlbumResponse responseGetAlbum = spotifyApiClientService.getAlbum(albumId);

            if(responseGetAlbum == null) {
                throw new Exception("Album was not found");
            }

            return TrackDetailsResponse.builder()
                    .name(trackItem.name())
                    .albumName(responseGetAlbum.name())
                    .playbackSeconds(trackItem.duration_ms())
                    .artistName(artist.name())
                    .isExplicit(trackItem.explicit())
                    .build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
