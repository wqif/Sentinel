package com.alibaba.csp.sentinel.dashboard.config.metric;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.MetricEntity;
import com.alibaba.csp.sentinel.dashboard.repository.metric.ElasticsearchMetricsRepository;
import com.alibaba.csp.sentinel.dashboard.repository.metric.MetricsRepository;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

/**
 * @author Will
 * @since 2.0.0
 */
@AutoConfigureAfter(ElasticsearchDataAutoConfiguration.class)
@ConditionalOnProperty(prefix = "sentinel-plus.metric.store", name = "type", havingValue = "es")
public class ElasticsearchMetricConfiguration {

    @Bean
    public MetricsRepository<MetricEntity> metricStore(final ElasticsearchRestTemplate elasticsearchRestTemplate,
                                                       MetricsStoreProperties metricsStoreProperties) {
        return new ElasticsearchMetricsRepository(elasticsearchRestTemplate, metricsStoreProperties);
    }

}
