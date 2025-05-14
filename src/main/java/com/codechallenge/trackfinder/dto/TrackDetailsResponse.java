package com.codechallenge.trackfinder.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Schema(
        name = "TrackDetailsResponse",
        description = "Complete details of a music track"
)
public class TrackDetailsResponse {
    @Schema(
            description = "Track name",
            example = "Shape of You",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String name;

    @Schema(
            description = "Artist name",
            example = "Ed Sheeran",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String artistName;

    @Schema(
            description = "Album name",
            example = "÷ (Divide)",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String albumName;

    @Schema(
            description = "Track duration in seconds",
            example = "233",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Integer playbackSeconds;

    @Schema(
            description = "Indicates if the track contains explicit content",
            example = "false",
            defaultValue = "false"
    )
    private Boolean isExplicit;

    @Schema(
            description = "URL of the cover image",
            example = "https://example.com/cover.jpg",
            nullable = true
    )
    private String coverUrl;
}
