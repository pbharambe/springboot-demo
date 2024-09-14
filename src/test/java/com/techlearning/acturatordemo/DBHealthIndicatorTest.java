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
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DBHealthIndicatorTest {

    @Mock
    private DataSource dataSource;

    @Mock
    private Connection connection;

    @InjectMocks
    private DBHealthIndicator dbHealthIndicator;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks
        dbHealthIndicator = new DBHealthIndicator(dataSource);
        //dbHealthIndicator.getHealth(true);

    }

    @Test
    @DisplayName("Test Database is UP")
    void testDatabaseIsUp() throws SQLException {
        when(dataSource.getConnection()).thenReturn(connection);
        DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
        when(connection.getMetaData()).thenReturn(databaseMetaData);
        when(connection.isValid(0)).thenReturn(true);
        when(databaseMetaData.getDatabaseProductName()).thenReturn("some-db");

        Health health = dbHealthIndicator.health();
        assertAll(
                () -> assertNotNull(health),
                () -> assertEquals(Status.UP, health.getStatus()),
                () -> assertEquals("Available", health.getDetails().get("Database"))
        );
    }

    @Test
    @DisplayName("Test Database is DOWN")
    public void testDatabaseIsDown() throws SQLException {
        when(dataSource.getConnection()).thenReturn(connection);
        DatabaseMetaData databaseMetaData = mock(DatabaseMetaData.class);
        when(connection.getMetaData()).thenReturn(databaseMetaData);
        when(connection.isValid(0)).thenReturn(false);
        when(databaseMetaData.getDatabaseProductName()).thenReturn("some-db");
        Health health = dbHealthIndicator.health();
        assertAll(
                () -> assertNotNull(health),
                () -> assertEquals(Status.DOWN, health.getStatus()),
                () -> assertEquals("Unavailable", health.getDetails().get("Database"))
        );
    }

    @Test
    @DisplayName("Test Database is DOWN")
    public void testDatabaseIsDown_IfAnyException() throws SQLException {
        when(dataSource.getConnection()).thenThrow(new SQLException("Connection failed"));
        Health health = dbHealthIndicator.health();
        assertAll(
                () -> assertNotNull(health),
                () -> assertEquals(Status.DOWN, health.getStatus()),
                () -> assertEquals("Unavailable", health.getDetails().get("Database"))
        );
    }
}
