package com.techlearning.acturatordemo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RemoteServiceHealthIndicatorTest {

    @Autowired
    RemoteServiceHealthIndicator remoteServiceHealthIndicator;

    @Test
    public void test_ServiceUp() {
        Health health = remoteServiceHealthIndicator.health();
        Assertions.assertAll(
                () -> assertEquals("UP", health.getStatus().getCode()),
                () -> assertEquals("Available", health.getDetails().get("RemoteService"))
        );
    }
}