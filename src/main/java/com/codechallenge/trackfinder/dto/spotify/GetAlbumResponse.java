package com.codechallenge.trackfinder.dto.spotify;

import java.util.List;

public record GetAlbumResponse(
        String id,
        String name,
        List<AlbumImage> images
) { }
