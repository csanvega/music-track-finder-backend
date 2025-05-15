# ISRC Track Manager - Backend

This is the backend service for the **ISRC Track Manager** platform. It is built using **Java 17** and **Spring Boot 3.4**.

## Main Services

- **createTrack**: Accepts an ISRC code and stores the corresponding track metadata in a local MySQL database.
- **getTrackMetadata**: Retrieves track metadata from the database based on a provided ISRC.
- **getCover**: Returns the album cover associated with a given track.

## Technologies Used

- Java 17
- Spring Boot 3.4
- Spring Data JPA
- MySQL
- Spotify Web API

## Configuration

This project uses a `application.yml` configuration file with the following environment variables:

```yaml
server:
  port: 8080

spring:
  application:
    name: trackfinder
    title: track-finder
    version: 1.0
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: ${URL_DB}
    username: ${USERNAME_DB}
    password: ${PASSWORD_DB}
  jpa:
    hibernate:
      ddl-auto: create
    database: mysql
    database-platform: org.hibernate.dialect.MySQL8Dialect

spotify-api:
  client-id: ${API_CLIENT_ID}
  client-secret: ${API_CLIENT_SECRET}
  grant-type: client_credentials
  url-auth: https://accounts.spotify.com/api/token
  url-api: https://api.spotify.com/v1

cors:
  allowed-origins: ${URL_FRONTEND}
```

Required Environment Variables

```
URL_DB: JDBC URL for your MySQL database
USERNAME_DB: Username for the database
PASSWORD_DB: Password for the database
API_CLIENT_ID: Spotify API client ID
API_CLIENT_SECRET: Spotify API client secret
URL_FRONTEND: Allowed origin for CORS (e.g., your frontend app URL)
```
```
# Build the project
./mvnw clean install

# Run the application
./mvnw spring-boot:run
```
By default, the application runs on port 8080.

## Enpoints 

```
# Create a Track
curl -X POST "http://localhost:8080/codechallenge/track" \
     -H "Content-Type: application/json" \
     -d '{"isrc": "USRC17607839"}'

# Get track metadata
curl -X GET "http://localhost:8080/codechallenge/track/USRC17607839" \
     -H "Accept: application/json"

# Get track image cover
curl -X GET "http://localhost:8080/codechallenge/track/USRC17607839/cover" \
     -H "Accept: image/*" \

```
