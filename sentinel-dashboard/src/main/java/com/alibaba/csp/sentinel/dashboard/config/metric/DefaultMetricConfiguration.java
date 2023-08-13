package com.alibaba.csp.sentinel.dashboard.config.metric;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.MetricEntity;
import com.alibaba.csp.sentinel.dashboard.repository.metric.InMemoryMetricsRepository;
import com.alibaba.csp.sentinel.dashboard.repository.metric.MetricsRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Will
 * @since 2.0.0
 */
@Configuration
@ConditionalOnProperty(prefix = "sentinel-plus.metric.store", name = "type", havingValue = "default", matchIfMissing = true)
public class DefaultMetricConfiguration {

    @Bean
    public MetricsRepository<MetricEntity> metricStore() {
        return new InMemoryMetricsRepository();
    }

}
