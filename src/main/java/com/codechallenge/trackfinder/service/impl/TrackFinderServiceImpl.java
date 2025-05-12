package com.codechallenge.trackfinder.service.impl;

import com.codechallenge.trackfinder.dto.*;
import com.codechallenge.trackfinder.entity.Track;
import com.codechallenge.trackfinder.repository.TrackRepository;
import com.codechallenge.trackfinder.service.SpotifyApiClientService;
import com.codechallenge.trackfinder.service.TrackFinderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TrackFinderServiceImpl implements TrackFinderService {

    private final SpotifyApiClientService spotifyApiClientService;
    private final TrackRepository trackRepository;

    @Transactional
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

            SpotifyAlbumImage coverImage = responseGetAlbum.images().stream()
                    .filter(img -> img.width() == 300)
                    .findFirst()
                    .orElseThrow(() -> new Exception("Image of 300x300 was not found"));;

            Track track = new Track();
            track.setId(trackItem.id());
            track.setIsrc(isrc);
            track.setName(trackItem.name());
            track.setArtistName(artist.name());
            track.setAlbumName(responseGetAlbum.name());
            track.setPlaybackSeconds(trackItem.duration_ms());
            track.setIsExplicit(trackItem.explicit());
            track.setCoverUrl(coverImage.url());

            trackRepository.save(track);

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
