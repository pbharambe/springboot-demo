package com.techlearning.acturatordemo;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.actuate.jdbc.DataSourceHealthIndicator;

import javax.sql.DataSource;

public class DBHealthIndicator implements HealthIndicator {

    private final DataSourceHealthIndicator dataSourceHealthIndicator;
    private final DataSource dataSource;

    public DBHealthIndicator(DataSource dataSource) {
        this.dataSource = dataSource;
        this.dataSourceHealthIndicator = new DataSourceHealthIndicator(dataSource);
        this.getHealth(true);
    }

    @Override
    public Health getHealth(boolean includeDetails) {
        if(includeDetails) {
            return HealthIndicator.super.getHealth(includeDetails);
        } else {
            return Health.up().build();
        }
    }

    @Override
    public Health health() {
        Health dbHealth = dataSourceHealthIndicator.health();
        //Health health = Status.UP.equals(dbHealth.getStatus()) ? Health.up().build() : Health.down().build();
        if (Status.UP.equals(dbHealth.getStatus())) {
            return Health.up().withDetail("Database", "Available").build();
        } else {
            return Health.down().withDetail("Database", "Unavailable").build();
        }
    }
}
