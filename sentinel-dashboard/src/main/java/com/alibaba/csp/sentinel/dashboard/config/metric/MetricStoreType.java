package com.alibaba.csp.sentinel.dashboard.config.metric;

/**
 * @author Will
 * @since 2.0.0
 */
public enum MetricStoreType {

    DEFAULT(DefaultMetricConfiguration.class),
    ES(ElasticsearchMetricConfiguration.class);

    private final Class<?> configurationClass;


    MetricStoreType(final Class<?> configurationClass) {
        this.configurationClass = configurationClass;
    }

    public Class<?> getConfigurationClass() {
        return configurationClass;
    }
}
