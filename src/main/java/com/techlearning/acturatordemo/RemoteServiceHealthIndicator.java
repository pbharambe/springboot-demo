package com.techlearning.acturatordemo;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;

import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@Component
public class RemoteServiceHealthIndicator implements HealthIndicator {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String REMOTE_SERVICE_URL = "https://www.yahoo.com/";

    @Override
    public Health health() {
        try {
            //String response = restTemplate.getForObject(REMOTE_SERVICE_URL, String.class);
            HttpStatusCode statusCode = RestClient.create().get().uri(REMOTE_SERVICE_URL).retrieve().toEntity(String.class).getStatusCode();
            if (HttpStatus.OK.equals(statusCode)) {
                return Health.up().withDetail("RemoteService", "Available").build();
            } else {
                return Health.down().withDetail("RemoteService", "Unavailable").build();
            }
        } catch (Exception e) {
            return Health.down(e).withDetail("RemoteService", "Error").build();
        }
    }
}
