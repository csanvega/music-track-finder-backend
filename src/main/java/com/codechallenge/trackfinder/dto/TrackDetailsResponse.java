package com.codechallenge.trackfinder.dto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TrackDetailsResponse {
    private String name;
    private String artistName;
    private String albumName;
    private Integer playbackSeconds;
    private Boolean isExplicit;
    private String coverUrl;
}
