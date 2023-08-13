/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.csp.sentinel.dashboard.rule.nacos;

import com.alibaba.csp.sentinel.dashboard.config.rule.NacosRuleStoreProperties;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.RuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleStore;
import com.alibaba.csp.sentinel.dashboard.rule.RuleConfigUtil;
import com.alibaba.csp.sentinel.dashboard.rule.RuleType;
import com.alibaba.csp.sentinel.dashboard.rule.aop.SentinelApiClientAspect;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.util.AssertUtil;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.ConfigType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Will
 * @since 2.0.0
 */
public class NacosDynamicRuleStore<T extends RuleEntity> extends DynamicRuleStore<T> {

    private static final Logger LOG = LoggerFactory.getLogger(SentinelApiClientAspect.class);

    private final NacosRuleStoreProperties nacosRuleStoreProperties;

    private final ConfigService configService;

    public NacosDynamicRuleStore(final RuleType ruleType,
                                 final NacosRuleStoreProperties nacosRuleStoreProperties,
                                 final ConfigService configService) {
        super.ruleType = ruleType;
        this.nacosRuleStoreProperties = nacosRuleStoreProperties;
        this.configService = configService;
    }

    @Override
    public List<T> getRules(final String appName) throws Exception {
        String dataId = this.getDataId(appName);
        String rules = configService.getConfig(dataId, nacosRuleStoreProperties.getGroup(), 3000);
        if (StringUtil.isEmpty(rules)) {
            return new ArrayList<>();
        }
        Converter<String, List<T>> decoder = RuleConfigUtil.getDecoder(ruleType.getClazz());
        return decoder.convert(rules);
    }

    @Override
    public void publish(final String app, final List<T> rules) throws Exception {
        String dataId = this.getDataId(app);
        AssertUtil.notEmpty(app, "app name cannot be empty");
        if (rules == null) {
            return;
        }
        Converter<Object, String> encoder = RuleConfigUtil.getEncoder();
        String content = encoder.convert(rules);
        configService.publishConfig(dataId, nacosRuleStoreProperties.getGroup(), content, ConfigType.JSON.getType());
        LOG.info("publish rule success - app: {}, type: {}, value: {}", app, ruleType.getName(), content);
    }

    private String getDataId(String appName) {
        return RuleConfigUtil.getDataId(appName, ruleType);
    }

}
