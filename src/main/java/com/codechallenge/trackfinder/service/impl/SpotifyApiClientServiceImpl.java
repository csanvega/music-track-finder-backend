package com.codechallenge.trackfinder.service.impl;

import com.codechallenge.trackfinder.config.SpotifyApiProperties;
import com.codechallenge.trackfinder.dto.SpotifyGetAlbumResponse;
import com.codechallenge.trackfinder.dto.SpotifySearchTrackResponse;
import com.codechallenge.trackfinder.dto.SpotifyTokenResponse;
import com.codechallenge.trackfinder.exception.SpotifyApiException;
import com.codechallenge.trackfinder.service.SpotifyApiClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SpotifyApiClientServiceImpl implements SpotifyApiClientService {

    private final SpotifyApiProperties spotifyApiProperties;
    private final WebClient webClient;

    @Override
    public String getToken() {
        try {
            SpotifyTokenResponse response = webClient
                    .post()
                    .uri(spotifyApiProperties.getUrlAuth())
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData("grant_type", spotifyApiProperties.getGrantType())
                            .with("client_id", spotifyApiProperties.getClientId())
                            .with("client_secret", spotifyApiProperties.getClientSecret()))
                    .retrieve()
                    .bodyToMono(SpotifyTokenResponse.class)
                    .block();

            return Objects.requireNonNull(response).access_token();

        } catch (Exception e) {
            throw new SpotifyApiException("Error spotify authorization");
        }
    }

    @Override
    public SpotifySearchTrackResponse searchTrack(String isrc) {
        String token = getToken();

        try {
            return webClient
                    .get()
                    .uri(spotifyApiProperties.getUrlApi() + "/search?q=isrc:" + isrc + "&type=track")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .bodyToMono(SpotifySearchTrackResponse.class)
                    .block();

        } catch (Exception e) {
            throw new SpotifyApiException("Error spotify search track API");
        }
    }

    @Override
    public SpotifyGetAlbumResponse getAlbum(String id) {
        String token = getToken();

        try {
            return webClient
                    .get()
                    .uri(spotifyApiProperties.getUrlApi() + "/albums/" + id)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .bodyToMono(SpotifyGetAlbumResponse.class)
                    .block();

        } catch (Exception e) {
            throw new SpotifyApiException("Error spotify get track metadata API");
        }
    }
}
