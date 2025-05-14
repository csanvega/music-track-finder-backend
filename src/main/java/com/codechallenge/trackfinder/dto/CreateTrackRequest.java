package com.codechallenge.trackfinder.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Schema(name = "CreateTrackRequest",
        description = "Request to create a music track using its ISRC code")
public class CreateTrackRequest {
    @NotBlank(message = "ISRC code is required")
    @Schema(description = "ISRC of the track",
            example = "USUM71703692",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String isrc;
}
