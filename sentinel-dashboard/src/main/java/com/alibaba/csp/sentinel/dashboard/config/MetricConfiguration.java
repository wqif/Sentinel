package com.alibaba.csp.sentinel.dashboard.config;

import com.alibaba.csp.sentinel.dashboard.config.MetricConfiguration.MetricConfigurationImportSelector;
import com.alibaba.csp.sentinel.dashboard.config.metric.MetricStoreType;
import com.alibaba.csp.sentinel.dashboard.config.metric.MetricsStoreProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author Will
 * @since 2.0.0
 */
@Configuration
@EnableConfigurationProperties(MetricsStoreProperties.class)
@Import({MetricConfigurationImportSelector.class})
public class MetricConfiguration {

    static class MetricConfigurationImportSelector implements ImportSelector {
        @Override
        public String[] selectImports(AnnotationMetadata importingClassMetadata) {
            MetricStoreType[] types = MetricStoreType.values();
            String[] imports = new String[types.length];
            for (int i = 0; i < types.length; i++) {
                imports[i] = types[i].getConfigurationClass().getName();
            }
            return imports;
        }
    }
}
