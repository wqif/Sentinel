package com.alibaba.csp.sentinel.dashboard.config.metric;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

/**
 * @author Will
 * @since 2.0.0
 */
@ConfigurationProperties(prefix = "sentinel-plus.metric.store")
public class MetricsStoreProperties {

    private String type = "default";

    private Duration maxQueryIntervalTime = Duration.ofMinutes(30);

    private Duration maxLiveTime = Duration.ofMinutes(10);

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Duration getMaxQueryIntervalTime() {
        return maxQueryIntervalTime;
    }

    public void setMaxQueryIntervalTime(final Duration maxQueryIntervalTime) {
        this.maxQueryIntervalTime = maxQueryIntervalTime;
    }

    public Duration getMaxLiveTime() {
        return maxLiveTime;
    }

    public void setMaxLiveTime(final Duration maxLiveTime) {
        this.maxLiveTime = maxLiveTime;
    }
}
