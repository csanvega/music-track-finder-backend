package com.codechallenge.trackfinder.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ImageCover {
    private String coverUrl;
    private byte[] imageCover;
    private String fileNameCover;
    private String contentTypeCover;
}
