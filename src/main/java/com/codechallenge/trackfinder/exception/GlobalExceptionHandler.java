package com.codechallenge.trackfinder.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<TrackFinderApiError> handleResourceNotFoundException(ResourceNotFoundException ex,
                                                                               WebRequest request) {
        TrackFinderApiError apiError = TrackFinderApiError.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<TrackFinderApiError>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<TrackFinderApiError> handleBadRequestException(BadRequestException ex,
                                                                         WebRequest request) {
        TrackFinderApiError apiError = TrackFinderApiError.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<TrackFinderApiError>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SpotifyApiException.class)
    public ResponseEntity<TrackFinderApiError> handleSpotifyApiException(SpotifyApiException ex,
                                                                         WebRequest request) {
        TrackFinderApiError apiError = TrackFinderApiError.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message("Error Spotify API:" + ex.getMessage())
                .build();

        return new ResponseEntity<TrackFinderApiError>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<TrackFinderApiError> handleGlobalException(Exception ex,
                                                                     WebRequest request) {
        TrackFinderApiError apiError = TrackFinderApiError.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message("Unexcepted error")
                .build();

        return new ResponseEntity<TrackFinderApiError>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<TrackFinderApiError> handleRuntimeException(RuntimeException ex, WebRequest request) {

        TrackFinderApiError apiError = TrackFinderApiError.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message("Unexcepted error")
                .build();

        return new ResponseEntity<TrackFinderApiError>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
