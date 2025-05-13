package com.codechallenge.trackfinder.service.impl;

import com.codechallenge.trackfinder.config.SpotifyApiProperties;
import com.codechallenge.trackfinder.dto.spotify.GetAlbumResponse;
import com.codechallenge.trackfinder.dto.spotify.SearchTrackResponse;
import com.codechallenge.trackfinder.dto.spotify.TokenResponse;
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
            TokenResponse response = webClient
                    .post()
                    .uri(spotifyApiProperties.getUrlAuth())
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData("grant_type", spotifyApiProperties.getGrantType())
                            .with("client_id", spotifyApiProperties.getClientId())
                            .with("client_secret", spotifyApiProperties.getClientSecret()))
                    .retrieve()
                    .bodyToMono(TokenResponse.class)
                    .block();

            return Objects.requireNonNull(response).access_token();

        } catch (Exception e) {
            throw new SpotifyApiException("Error spotify authorization");
        }
    }

    @Override
    public SearchTrackResponse searchTrack(String isrc) {
        String token = getToken();

        try {
            return webClient
                    .get()
                    .uri(spotifyApiProperties.getUrlApi() + "/search?q=isrc:" + isrc + "&type=track")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .bodyToMono(SearchTrackResponse.class)
                    .block();

        } catch (Exception e) {
            throw new SpotifyApiException("Error spotify search track API");
        }
    }

    @Override
    public GetAlbumResponse getAlbum(String id) {
        String token = getToken();

        try {
            return webClient
                    .get()
                    .uri(spotifyApiProperties.getUrlApi() + "/albums/" + id)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .bodyToMono(GetAlbumResponse.class)
                    .block();

        } catch (Exception e) {
            throw new SpotifyApiException("Error spotify get track metadata API");
        }
    }
}
