package com.codechallenge.trackfinder.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="tracks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(name = "cover_url", length = 2048)
    private String coverUrl;

}
