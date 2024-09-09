package com.techlearning.acturatordemo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class DatabaseHealthIndicatorTest {

    @Mock
    private DataSource dataSource;

    @Mock
    private Connection connection;

    @InjectMocks
    private DatabaseHealthIndicator databaseHealthIndicator;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);  // Initialize mocks
        when(dataSource.getConnection()).thenReturn(connection);  // Mock dataSource behavior
    }

    @Test
    @DisplayName("Test Database is UP")
    public void testDatabaseIsUp() throws SQLException {
        // Mock a valid connection
        when(connection.isValid(1000)).thenReturn(true);

        Health health = databaseHealthIndicator.health();
        assertEquals(Status.UP, health.getStatus());
        assertEquals("Available", health.getDetails().get("Database"));
    }

    @Test
    @DisplayName("Test Database is DOWN")
    public void testDatabaseIsDown() throws SQLException {
        // Mock an invalid connection
        when(connection.isValid(1000)).thenReturn(false);

        Health health = databaseHealthIndicator.health();
        assertEquals(Status.DOWN, health.getStatus());
        assertEquals("Unavailable", health.getDetails().get("Database"));
    }

    @Test
    @DisplayName("Test Database Connection Error")
    public void testDatabaseConnectionError() throws SQLException {
        // Mock a SQLException
        when(dataSource.getConnection()).thenThrow(new SQLException("Connection error"));

        Health health = databaseHealthIndicator.health();
        assertEquals(Status.DOWN, health.getStatus());
        assertEquals("Error", health.getDetails().get("Database"));
        assertEquals("java.sql.SQLException: Connection error", health.getDetails().get("error").toString());
    }
}
