package com.codechallenge.trackfinder.controller;

import com.codechallenge.trackfinder.dto.CreateTrackRequest;
import com.codechallenge.trackfinder.dto.TrackCoverResponse;
import com.codechallenge.trackfinder.dto.TrackDetailsResponse;
import com.codechallenge.trackfinder.service.TrackFinderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/codechallenge")
@RequiredArgsConstructor
public class TrackFinderController {

    private final TrackFinderService trackFinderService;

    @PostMapping("/track")
    public ResponseEntity<TrackDetailsResponse> createTrack(@RequestBody CreateTrackRequest request) {
        TrackDetailsResponse trackDetails = trackFinderService.createTrack(request.getIsrc());
        return ResponseEntity.ok(trackDetails);
    }

    @GetMapping("/track/{isrc}")
    public ResponseEntity<TrackDetailsResponse> getTrackMetadata(@PathVariable String isrc) {
        TrackDetailsResponse trackDetails = trackFinderService.getTrackMetadata(isrc);
        return ResponseEntity.ok(trackDetails);
    }

    @GetMapping("/track/{isrc}/cover")
    public ResponseEntity<TrackCoverResponse> getCover(@PathVariable String isrc) {
        TrackCoverResponse trackCover = trackFinderService.getCover(isrc);
        return ResponseEntity.ok(trackCover);
    }

}
