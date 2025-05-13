package com.codechallenge.trackfinder.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="tracks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Track {
    @Id
    @Column(name = "id", nullable = false, unique = true, length = 50)
    private String id;

    @Column(name = "isrc", nullable = false, unique = true, length = 20)
    private String isrc;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "artist_name")
    private String artistName;

    @Column(name = "album_name")
    private String albumName;

    @Column(name = "playback_seconds")
    private Integer playbackSeconds;

    @Column(name = "is_explicit")
    private Boolean isExplicit;

    @Column(name = "cover_url", length = 500)
    private String coverUrl;

    @Lob
    @Column(name = "image_cover", columnDefinition = "LONGBLOB")
    private byte[] imageCover;

    @Column(name = "file_name_cover")
    private String fileNameCover;

    @Column(name = "content_type_cover")
    private String contentTypeCover;

}
