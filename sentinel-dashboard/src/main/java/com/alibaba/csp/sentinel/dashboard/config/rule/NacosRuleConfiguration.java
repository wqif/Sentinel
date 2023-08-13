package com.alibaba.csp.sentinel.dashboard.config.rule;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.gateway.ApiDefinitionEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.gateway.GatewayFlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.*;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleStore;
import com.alibaba.csp.sentinel.dashboard.rule.RuleType;
import com.alibaba.csp.sentinel.dashboard.rule.aop.SentinelApiClientAspect;
import com.alibaba.csp.sentinel.dashboard.rule.nacos.NacosDynamicRuleStore;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigFactory;
import com.alibaba.nacos.api.config.ConfigService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.Properties;

/**
 * @author Will
 * @since 2.0.0
 */
@Configuration
@ConditionalOnProperty(prefix = "sentinel-plus.rule.store", name = "type", havingValue = "nacos")
@EnableConfigurationProperties(NacosRuleStoreProperties.class)
public class NacosRuleConfiguration {
    
    @Resource
    private NacosRuleStoreProperties nacosRuleStoreProperties;
    
    @Bean
    public ConfigService configService() throws Exception {
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.NAMESPACE, nacosRuleStoreProperties.getNamespace());
        properties.setProperty(PropertyKeyConst.SERVER_ADDR, nacosRuleStoreProperties.getServerAddr());
        properties.setProperty(PropertyKeyConst.USERNAME, nacosRuleStoreProperties.getUsername());
        properties.setProperty(PropertyKeyConst.PASSWORD, nacosRuleStoreProperties.getPassword());
        return ConfigFactory.createConfigService(properties);
    }
    
    @Bean
    public SentinelApiClientAspect sentinelApiClientAspect() {
        return new SentinelApiClientAspect();
    }

    @Bean
    public DynamicRuleStore<FlowRuleEntity> flowRuleDynamicRuleStore(ConfigService configService) {
        return new NacosDynamicRuleStore<>(RuleType.FLOW, nacosRuleStoreProperties, configService);
    }

    @Bean
    public DynamicRuleStore<DegradeRuleEntity> degradeRuleDynamicRuleStore(ConfigService configService) {
        return new NacosDynamicRuleStore<>(RuleType.DEGRADE, nacosRuleStoreProperties, configService);
    }

    @Bean
    public DynamicRuleStore<CustomParamFlowRuleEntity> paramFlowRuleDynamicRuleStore(ConfigService configService) {
        return new NacosDynamicRuleStore<>(
                RuleType.PARAM_FLOW, nacosRuleStoreProperties, configService);
    }

    @Bean
    public DynamicRuleStore<SystemRuleEntity> systemRuleDynamicRuleStore(ConfigService configService) {
        return new NacosDynamicRuleStore<>(RuleType.SYSTEM, nacosRuleStoreProperties, configService);
    }

    @Bean
    public DynamicRuleStore<CustomAuthorityRuleEntity> authorityRuleDynamicRuleStore(ConfigService configService) {
        return new NacosDynamicRuleStore<>(RuleType.AUTHORITY, nacosRuleStoreProperties, configService);
    }

    @Bean
    public DynamicRuleStore<GatewayFlowRuleEntity> gatewayFlowRuleDynamicRuleStore(ConfigService configService) {
        return new NacosDynamicRuleStore<>(RuleType.GW_FLOW, nacosRuleStoreProperties, configService);
    }

    @Bean
    public DynamicRuleStore<ApiDefinitionEntity> apiDefinitionDynamicRuleStore(ConfigService configService) {
        return new NacosDynamicRuleStore<>(RuleType.GW_API_GROUP, nacosRuleStoreProperties, configService);
    }
    
}
