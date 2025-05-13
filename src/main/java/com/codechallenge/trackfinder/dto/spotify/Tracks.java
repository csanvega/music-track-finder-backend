package com.codechallenge.trackfinder.dto.spotify;

import java.util.List;

public record Tracks(
        String href,
        List<TrackItem> items,
        Integer total
) { }
