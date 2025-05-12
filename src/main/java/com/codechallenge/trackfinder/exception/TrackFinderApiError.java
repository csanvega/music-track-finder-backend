package com.codechallenge.trackfinder.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TrackFinderApiError {
    private int status;
    private String error;
    private String message;
}
