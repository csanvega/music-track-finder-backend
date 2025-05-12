package com.codechallenge.trackfinder.controller;

import com.codechallenge.trackfinder.dto.CreateTrackRequest;
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

    @PostMapping("/tracks")
    public ResponseEntity<TrackDetailsResponse> createTrack(@RequestBody CreateTrackRequest request) {
        System.out.println("isrc" + request.getIsrc());
        TrackDetailsResponse trackDetails = trackFinderService.createTrack(request.getIsrc());
        return ResponseEntity.ok(trackDetails);
    }
}
