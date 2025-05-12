package com.codechallenge.trackfinder.service.impl;

import com.codechallenge.trackfinder.config.SpotifyApiProperties;
import com.codechallenge.trackfinder.dto.SpotifyTokenResponse;
import com.codechallenge.trackfinder.service.SpotifyApiAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SpotifyApiAuthServiceImpl implements SpotifyApiAuthService {

    private final SpotifyApiProperties spotifyApiProperties;
    private final RestTemplate restTemplate;

    @Override
    public String getToken() {

        HttpHeaders headers =  new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", spotifyApiProperties.getGrantType());
        body.add("client_id", spotifyApiProperties.getClientId());
        body.add("client_secret", spotifyApiProperties.getClientSecret());

        HttpEntity<MultiValueMap<String, String>> requestBody = new HttpEntity<>(body, headers);

        ResponseEntity<SpotifyTokenResponse> response = restTemplate.exchange(
                spotifyApiProperties.getUrlAuth(),
                HttpMethod.POST,
                requestBody,
                SpotifyTokenResponse.class
        );

        return Objects.requireNonNull(response.getBody()).getAccess_token();
    }
}
