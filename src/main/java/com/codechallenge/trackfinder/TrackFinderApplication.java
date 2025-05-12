package com.codechallenge.trackfinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class TrackFinderApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrackFinderApplication.class, args);
	}

}
