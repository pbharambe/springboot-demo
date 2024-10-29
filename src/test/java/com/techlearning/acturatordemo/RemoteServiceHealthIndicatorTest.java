package com.techlearning.acturatordemo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RemoteServiceHealthIndicatorTest {

    @Autowired
    RemoteServiceHealthIndicator remoteServiceHealthIndicator;

    @Test
    void test_Service_Is_UP() throws Exception {
        remoteServiceHealthIndicator.setRemoteServiceURL("https://httpstat.us/200");
        Health health = remoteServiceHealthIndicator.health();
        Assertions.assertAll(
                () -> assertEquals("UP", health.getStatus().getCode()),
                () -> assertEquals("Available", health.getDetails().get("RemoteService"))
        );
    }

    @Test
    void test_Service_Is_DOWN_Error() throws Exception {
        remoteServiceHealthIndicator.setRemoteServiceURL("https://httpstat.us/400");
        Health health = remoteServiceHealthIndicator.health();
        Assertions.assertAll(
                () -> assertEquals("DOWN", health.getStatus().getCode()),
                () -> assertEquals("Error or Unavailable", health.getDetails().get("RemoteService"))
        );
    }
}
