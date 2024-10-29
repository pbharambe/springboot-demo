package com.techlearning.acturatordemo;

import org.springframework.beans.factory.annotation.Value;
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
    //private static final String REMOTE_SERVICE_URL = "https://www.yahoo.com/";

    @Value("${remote.service.url}")
    private String REMOTE_SERVICE_URL;

    @Override
    public Health health() {
        try {
            //String response = restTemplate.getForObject(REMOTE_SERVICE_URL, String.class);
            HttpStatusCode statusCode = RestClient.create().get().uri(REMOTE_SERVICE_URL).retrieve().toEntity(String.class).getStatusCode();
            if (HttpStatus.OK.equals(statusCode)) {
                return Health.up().withDetail("RemoteService", "Available").build();
            }
        } catch (Exception e) {
            return Health.down(e).withDetail("RemoteService", "Error or Unavailable").build();
        }
        return null;
    }

    public void setRemoteServiceURL(String url) {
        REMOTE_SERVICE_URL = url;
    }
}
