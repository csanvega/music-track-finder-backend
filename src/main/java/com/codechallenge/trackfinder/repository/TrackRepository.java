package com.codechallenge.trackfinder.repository;

import com.codechallenge.trackfinder.entity.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrackRepository extends JpaRepository<Track, String> {
    Optional<Track> findByIsrc(String isrc);
    boolean existsByIsrc(String isrc);
}
