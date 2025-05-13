package com.codechallenge.trackfinder.exception;

public abstract class TrackFinderException extends RuntimeException {
    public TrackFinderException(String message) {
        super(message);
    }
}
