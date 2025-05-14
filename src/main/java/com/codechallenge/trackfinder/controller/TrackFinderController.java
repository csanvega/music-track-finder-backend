package com.codechallenge.trackfinder.controller;

import com.codechallenge.trackfinder.dto.CreateTrackRequest;
import com.codechallenge.trackfinder.dto.ImageCover;
import com.codechallenge.trackfinder.dto.TrackDetailsResponse;
import com.codechallenge.trackfinder.service.TrackFinderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/codechallenge")
@CrossOrigin("${cors.allowed-origins}")
public class TrackFinderController {

    private final TrackFinderService trackFinderService;

    public TrackFinderController(TrackFinderService trackFinderService) {
        this.trackFinderService = trackFinderService;
    }

    @PostMapping("/track")
    public ResponseEntity<TrackDetailsResponse> createTrack(@RequestBody CreateTrackRequest request) {
        TrackDetailsResponse trackDetails = trackFinderService.createTrack(request.getIsrc());
        return ResponseEntity.status(HttpStatus.CREATED).body(trackDetails);
    }

    @GetMapping("/track/{isrc}")
    public ResponseEntity<TrackDetailsResponse> getTrackMetadata(@PathVariable String isrc) {
        TrackDetailsResponse trackDetails = trackFinderService.getTrackMetadata(isrc);
        return ResponseEntity.ok(trackDetails);
    }

    @GetMapping("/track/{isrc}/cover")
    public ResponseEntity<?> getCover(@PathVariable String isrc) {
        ImageCover trackCover = trackFinderService.getCover(isrc);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(trackCover.getContentTypeCover()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + trackCover.getFileNameCover() + "\"")
                .body(trackCover.getImageCover());
    }

}
