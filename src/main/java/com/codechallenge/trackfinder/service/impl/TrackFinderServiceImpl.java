package com.codechallenge.trackfinder.service.impl;

import com.codechallenge.trackfinder.dto.*;
import com.codechallenge.trackfinder.entity.Track;
import com.codechallenge.trackfinder.exception.BadRequestException;
import com.codechallenge.trackfinder.exception.ResourceNotFoundException;
import com.codechallenge.trackfinder.exception.SpotifyApiException;
import com.codechallenge.trackfinder.repository.TrackRepository;
import com.codechallenge.trackfinder.service.SpotifyApiClientService;
import com.codechallenge.trackfinder.service.TrackFinderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrackFinderServiceImpl implements TrackFinderService {

    private final SpotifyApiClientService spotifyApiClientService;
    private final TrackRepository trackRepository;

    @Transactional
    public TrackDetailsResponse createTrack(String isrc) {
        try {
            Optional<Track> trackOptional = trackRepository.findByIsrc(isrc);
            if (trackOptional.isPresent()) {
                throw new BadRequestException("Track already exists ISRC:" + isrc);
            }

            SpotifySearchTrackResponse responseSearch = spotifyApiClientService.searchTrack(isrc);

            if (responseSearch == null
                    || responseSearch.tracks() == null
                    || responseSearch.tracks().items() == null
                    || responseSearch.tracks().items().isEmpty()) {
                throw new ResourceNotFoundException("Track", "isrc", isrc);
            }

            SpotifyTrackItem trackItem = responseSearch.tracks().items().get(0);
            SpotifyArtist artist = trackItem.artists().get(0);
            String albumId = trackItem.album().id();

            SpotifyGetAlbumResponse responseGetAlbum = spotifyApiClientService.getAlbum(albumId);

            if (responseGetAlbum == null) {
                throw new ResourceNotFoundException("Album", "isrc", isrc);
            }

            SpotifyAlbumImage coverImage = responseGetAlbum.images().stream()
                    .filter(img -> img.width() == 300)
                    .findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException("Image", "isrc", isrc));

            Track track = Track.builder()
                    .id(trackItem.id())
                    .isrc(isrc)
                    .name(trackItem.name())
                    .artistName(artist.name())
                    .albumName(responseGetAlbum.name())
                    .playbackSeconds(trackItem.duration_ms())
                    .isExplicit(trackItem.explicit())
                    .coverUrl(coverImage.url())
                    .build();

            trackRepository.save(track);

            return TrackDetailsResponse.builder()
                    .name(trackItem.name())
                    .albumName(responseGetAlbum.name())
                    .playbackSeconds(trackItem.duration_ms())
                    .artistName(artist.name())
                    .isExplicit(trackItem.explicit())
                    .coverUrl(coverImage.url())
                    .build();

        } catch (ResourceNotFoundException | SpotifyApiException | BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected Error creating track with ISRC" + isrc, e);
        }
    }

    public TrackDetailsResponse getTrackMetadata(String isrc) {
        try {
            Track track = trackRepository.findByIsrc(isrc)
                    .orElseThrow(() -> new ResourceNotFoundException("Track","isrc",isrc));

            return TrackDetailsResponse.builder()
                    .name(track.getName())
                    .albumName(track.getAlbumName())
                    .playbackSeconds(track.getPlaybackSeconds())
                    .artistName(track.getArtistName())
                    .isExplicit(track.getIsExplicit())
                    .coverUrl(track.getCoverUrl())
                    .build();

        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error getting track metadata",e);
        }
    }

    @Override
    public TrackCoverResponse getCover(String isrc) {
        try {
            Track track = trackRepository.findByIsrc(isrc)
                    .orElseThrow(() -> new ResourceNotFoundException("Cover","isrc",isrc));

            return TrackCoverResponse.builder()
                    .coverUrl(track.getCoverUrl())
                    .build();

        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error getting track metadata",e);
        }
    }
}
