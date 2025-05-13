package com.codechallenge.trackfinder.dto.spotify;

import java.util.List;

public record TrackItem(
        String id,
        Integer duration_ms,
        Boolean explicit,
        String name,
        ExternalIds external_ids,
        Album album,
        List<Artist> artists
) { }
