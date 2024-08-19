package com.techlearning.acturatordemo;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Service;

@Service
@Endpoint(id = "test-endpoint")
public class TestEndPoint {

    @ReadOperation
    public int random() {
        return (int) (Math.random() * 100);
    }
}
