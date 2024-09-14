/*
package com.techlearning.acturatordemo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.jdbc.datasource.AbstractDataSource;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DatabaseHealthIndicatorIntTest {

    @Autowired
    private DatabaseHealthIndicator databaseHealthIndicator;

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    public void setup()  {
        databaseHealthIndicator = new DatabaseHealthIndicator(dataSource);
    }

    @Test
    @DisplayName("Test Database is UP")
    void testDatabaseIsUp() {
        Health health = databaseHealthIndicator.health();
        assertAll(
                () -> assertNotNull(health),
                () -> assertEquals("UP", health.getStatus().getCode()),
                () -> assertEquals("Available", health.getDetails().get("Database"))
        );
    }
}
*/
