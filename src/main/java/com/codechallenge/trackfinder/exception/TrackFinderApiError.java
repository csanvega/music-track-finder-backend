package com.codechallenge.trackfinder.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Schema(description = "Standard error response")
public class TrackFinderApiError {
    @Schema(description = "HTTP status code", example = "404")
    private int status;
    @Schema(description = "Error description")
    private String error;
    @Schema(description = "Error message")
    private String message;
}
