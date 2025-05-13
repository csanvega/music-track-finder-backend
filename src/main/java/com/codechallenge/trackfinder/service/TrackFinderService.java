package com.codechallenge.trackfinder.service;

import com.codechallenge.trackfinder.dto.ImageCover;
import com.codechallenge.trackfinder.dto.TrackDetailsResponse;

public interface TrackFinderService {
    TrackDetailsResponse createTrack(String isrc);
    TrackDetailsResponse getTrackMetadata(String isrc);
    ImageCover getCover(String isrc);
}
