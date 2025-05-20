package com.codechallenge.trackfinder.controller;

import com.codechallenge.trackfinder.dto.CreateTrackRequest;
import com.codechallenge.trackfinder.dto.ImageCover;
import com.codechallenge.trackfinder.dto.TrackDetailsResponse;
import com.codechallenge.trackfinder.exception.TrackFinderApiError;
import com.codechallenge.trackfinder.service.TrackFinderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/codechallenge")
@Tag(name = "Track Finder", description = "API for retrieving track information")
public class TrackFinderController {

    private final TrackFinderService trackFinderService;

    public TrackFinderController(TrackFinderService trackFinderService) {
        this.trackFinderService = trackFinderService;
    }

    @Operation(
            summary = "Create a track",
            description = "Creates a new track using its ISRC code"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Track successfully created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TrackDetailsResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid ISRC code",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TrackFinderApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Track not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TrackFinderApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TrackFinderApiError.class)
                    )
            )
    })
    @PostMapping("/track")
    public ResponseEntity<TrackDetailsResponse> createTrack(@RequestBody CreateTrackRequest request) {
        TrackDetailsResponse trackDetails = trackFinderService.createTrack(request.getIsrc());
        return ResponseEntity.status(HttpStatus.CREATED).body(trackDetails);
    }

    @Operation(
            summary = "Get track metadata",
            description = "Retrieves the metadata of a music track using its ISRC code"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Metadata successfully retrieved",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TrackDetailsResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Track not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TrackFinderApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TrackFinderApiError.class)
                    )
            )
    })
    @GetMapping("/track/{isrc}")
    public ResponseEntity<TrackDetailsResponse> getTrackMetadata(@PathVariable String isrc) {
        TrackDetailsResponse trackDetails = trackFinderService.getTrackMetadata(isrc);
        return ResponseEntity.ok(trackDetails);
    }

    @Operation(
            summary = "Get track cover image",
            description = "Retrieves the cover image of a music track using its ISRC code"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Image successfully retrieved",
                    content = @Content(mediaType = "image/*")
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Track or image not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TrackFinderApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TrackFinderApiError.class)
                    )
            )
    })
    @GetMapping("/track/{isrc}/cover")
    public ResponseEntity<?> getCover(@PathVariable String isrc) {
        ImageCover trackCover = trackFinderService.getCover(isrc);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(trackCover.getContentTypeCover()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + trackCover.getFileNameCover() + "\"")
                .body(trackCover.getImageCover());
    }

}
