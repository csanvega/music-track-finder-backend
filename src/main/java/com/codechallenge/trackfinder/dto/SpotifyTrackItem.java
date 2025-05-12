package com.codechallenge.trackfinder.dto;

import java.util.List;

public record SpotifyTrackItem(
        String id,
        Integer duration_ms,
        Boolean explicit,
        String name,
        SpotifyExternalIds external_ids,
        SpotifyAlbum album,
        List<SpotifyArtist> artists
) { }
