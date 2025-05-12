package com.codechallenge.trackfinder.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "spotify-api")
@Component
@Getter
@Setter
public class SpotifyApiProperties {
    private String clientId;
    private String clientSecret;
    private String urlAuth;
    private String urlApi;
    private String grantType;
}
