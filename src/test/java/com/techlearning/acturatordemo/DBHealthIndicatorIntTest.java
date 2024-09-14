//package com.techlearning.acturatordemo;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.actuate.health.Health;
//import org.springframework.boot.actuate.health.Status;
//import org.springframework.boot.actuate.jdbc.DataSourceHealthIndicator;
//
//import javax.sql.DataSource;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class DBHealthIndicatorIntTest {
//
//    @Autowired
//    private DBHealthIndicator dbHealthIndicator;
//
//    @Autowired
//    private DataSourceHealthIndicator dataSourceHealthIndicator;
//
//    @Autowired
//    private DataSource dataSource;
//
//    @BeforeEach
//    public void setup() {
//        dbHealthIndicator = new DBHealthIndicator(dataSource);
//    }
//
//    @Test
//    @DisplayName("Test DB UP Status")
//    void testDatabaseIsUp() {
//        Health health = dbHealthIndicator.health();
//        assertAll(
//                () -> assertNotNull(health),
//                () -> assertEquals(Status.UP, health.getStatus())
//        );
//    }
//}
