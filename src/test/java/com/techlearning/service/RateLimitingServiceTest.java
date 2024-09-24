package com.techlearning.service;

import io.github.bucket4j.Bucket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@SpringBootTest
class RateLimitingServiceTest {

    private RateLimitingService rateLimitingService;
    private Bucket mockBucket;

    @BeforeEach
    void setup() {
        mockBucket = mock(Bucket.class);  // Mock the Bucket
        rateLimitingService = new RateLimitingService() {
            // Override to provide the mocked bucket
            @Override
            public Bucket createNewBucket(String apiKey) {
                return mockBucket;  // Return mocked bucket
            }
        };
    }

    @Test
    void allowRequest_whenBucketAllows_shouldReturnTrue() {
        // Mock the behavior of the bucket to allow consumption
        when(mockBucket.tryConsume(1)).thenReturn(true);
        boolean allowed = rateLimitingService.allowRequest("test-api-key");
        assertTrue(allowed);
        verify(mockBucket, times(1)).tryConsume(1);  // Verify that tryConsume was called
    }

    @Test
    void allowRequest_whenBucketDenies_shouldReturnFalse() {
        // Mock the behavior of the bucket to deny consumption
        when(mockBucket.tryConsume(1)).thenReturn(false);
        boolean allowed = rateLimitingService.allowRequest("test-api-key");
        assertFalse(allowed);
        verify(mockBucket, times(1)).tryConsume(1);  // Verify that tryConsume was called
    }

    @Test
    void allowRequest_multipleRequests_shouldRespectRateLimiting() {
        // First 10 requests are allowed
        when(mockBucket.tryConsume(1)).thenReturn(true);
        for (int i = 0; i < 10; i++) {
            boolean allowed = rateLimitingService.allowRequest("test-api-key");
            assertTrue(allowed);
        }

        // After 10 requests, rate limiting should apply and deny
        when(mockBucket.tryConsume(1)).thenReturn(false);
        boolean allowed = rateLimitingService.allowRequest("test-api-key");
        assertFalse(allowed);

        // Verify tryConsume was called exactly 11 times (10 allowed, 1 denied)
        verify(mockBucket, times(11)).tryConsume(1);
    }

}