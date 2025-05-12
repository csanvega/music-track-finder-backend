package com.codechallenge.trackfinder.dto;

import java.util.List;

public record SpotifyGetAlbumResponse(
        String id,
        String name,
        List<SpotifyAlbumImage> images
) { }
