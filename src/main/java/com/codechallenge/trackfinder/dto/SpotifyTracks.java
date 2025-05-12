package com.codechallenge.trackfinder.dto;

import java.util.List;

public record SpotifyTracks(
        String href,
        List<SpotifyTrackItem> items,
        Integer total
) { }
