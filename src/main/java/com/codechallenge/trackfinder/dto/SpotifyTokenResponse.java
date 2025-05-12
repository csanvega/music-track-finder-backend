package com.codechallenge.trackfinder.dto;

public record SpotifyTokenResponse(
        String access_token,
        String token_type,
        int expires_in
) { }