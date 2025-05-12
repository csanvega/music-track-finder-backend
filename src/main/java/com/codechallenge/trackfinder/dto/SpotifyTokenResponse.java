package com.codechallenge.trackfinder.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpotifyTokenResponse {
    private String access_token;
    private String token_type;
    private int expires_in;
}
