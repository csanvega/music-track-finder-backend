package com.codechallenge.trackfinder.service;

import com.codechallenge.trackfinder.dto.TrackCoverResponse;
import com.codechallenge.trackfinder.dto.TrackDetailsResponse;

public interface TrackFinderService {
    TrackDetailsResponse createTrack(String isrc);
    TrackDetailsResponse getTrackMetadata(String isrc);
    TrackCoverResponse getCover(String isrc);
}
