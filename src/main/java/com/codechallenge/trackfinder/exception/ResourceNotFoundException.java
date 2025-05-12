package com.codechallenge.trackfinder.exception;

public class ResourceNotFoundException extends TrackFinderException {
    public ResourceNotFoundException(String resource, String field, Object value) {
        super(String.format("%s not found. %s: '%s'", resource, field, value));

    }
}