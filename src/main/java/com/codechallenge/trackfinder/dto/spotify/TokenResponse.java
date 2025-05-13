package com.codechallenge.trackfinder.dto.spotify;

public record TokenResponse(
        String access_token,
        String token_type,
        int expires_in
) { }