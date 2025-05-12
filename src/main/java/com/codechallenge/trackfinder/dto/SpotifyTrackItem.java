package com.codechallenge.trackfinder.dto;

public record SpotifyTrackItem(
        String id,
        Integer duration_ms,
        Boolean explicit,
        String name,
        SpotifyExternalIds external_ids,
        SpotifyAlbum album
) { }
