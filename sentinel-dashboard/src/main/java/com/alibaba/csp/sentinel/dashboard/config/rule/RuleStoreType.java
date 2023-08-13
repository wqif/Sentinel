package com.alibaba.csp.sentinel.dashboard.config.rule;

/**
 * @author Will
 * @since 2.0.0
 */
public enum RuleStoreType {

    DEFAULT(DefaultRuleConfiguration.class),
    NACOS(NacosRuleConfiguration.class);

    private final Class<?> configurationClass;

    RuleStoreType(final Class<?> configurationClass) {
        this.configurationClass = configurationClass;
    }

    public Class<?> getConfigurationClass() {
        return configurationClass;
    }
}
