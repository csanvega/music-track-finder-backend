package com.codechallenge.trackfinder.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Schema(name = "ImageCover", description = "Cover image of a music track")
public class ImageCover {
    private String coverUrl;
    private byte[] imageCover;
    private String fileNameCover;
    private String contentTypeCover;
}
