package com.codechallenge.trackfinder.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateTrackRequest {
    private String isrc;
}
