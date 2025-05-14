package com.codechallenge.trackfinder.service.impl;

import com.codechallenge.trackfinder.config.SpotifyApiProperties;
import com.codechallenge.trackfinder.dto.ImageCover;
import com.codechallenge.trackfinder.dto.spotify.GetAlbumResponse;
import com.codechallenge.trackfinder.dto.spotify.SearchTrackResponse;
import com.codechallenge.trackfinder.dto.spotify.TokenResponse;
import com.codechallenge.trackfinder.exception.SpotifyApiException;
import com.codechallenge.trackfinder.service.SpotifyApiClientService;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

@Service
public class SpotifyApiClientServiceImpl implements SpotifyApiClientService {

    private final SpotifyApiProperties spotifyApiProperties;
    private final WebClient webClient;

    public SpotifyApiClientServiceImpl(
            SpotifyApiProperties spotifyApiProperties,
            WebClient webClient) {
        this.spotifyApiProperties = spotifyApiProperties;
        this.webClient = webClient;
    }

    private String getContentType(String coverUrl) {
        try {
            URL url = new URL(coverUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode != HttpStatus.OK.value()) {
                throw new IllegalArgumentException("The URL cannot be accessed" + responseCode);
            }

            String contentType = connection.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                throw new IllegalArgumentException("The image could not be downloaded");
            }
            return contentType;

        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected Error downloading the image", e);
        }
    }

    public ImageCover getCoverImageFromUrl(String coverUrl, String isrc) {
        try {
            String contentType = getContentType(coverUrl);

            byte[] imageBytes = webClient.get()
                    .uri(coverUrl)
                    .retrieve()
                    .bodyToMono(byte[].class)
                    .block();

            if (imageBytes == null || imageBytes.length == 0) {
                throw new IllegalArgumentException("The image could not be downloaded from the URL.");
            }

            return ImageCover.builder()
                    .coverUrl(coverUrl)
                    .fileNameCover(isrc)
                    .contentTypeCover(contentType)
                    .imageCover(imageBytes)
                    .build();

        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected Error downloading the image", e);
        }
    }

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
