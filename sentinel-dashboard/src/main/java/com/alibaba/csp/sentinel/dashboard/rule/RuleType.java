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

package com.alibaba.csp.sentinel.dashboard.rule;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.gateway.ApiDefinitionEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.gateway.GatewayFlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.*;
import com.alibaba.csp.sentinel.slots.block.AbstractRule;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author Will
 * @since 2.0.0
 */
public enum RuleType {

    /**
     * 流控规则
     */
    FLOW("flow-rules", FlowRuleEntity.class),
    /**
     * 熔断规则
     */
    DEGRADE("degrade-rules", DegradeRuleEntity.class),
    /**
     * 热点规则
     */
    PARAM_FLOW("param-flow-rules", CustomParamFlowRuleEntity.class),
    /**
     * 系统规则
     */
    SYSTEM("system-rules", SystemRuleEntity.class),
    /**
     * 授权规则
     */
    AUTHORITY("authority-rules", CustomAuthorityRuleEntity.class),
    /**
     * 网关流控规则
     */
    GW_FLOW("gw-flow-rules", GatewayFlowRuleEntity.class),
    /**
     * api 分组
     */
    GW_API_GROUP("gw-api-group-rules", ApiDefinitionEntity.class);

    /**
     * alias for {@link AbstractRule}.
     */
    private final String name;

    /**
     * concrete {@link AbstractRule} class.
     */
    private final Class<? extends RuleEntity> clazz;

    RuleType(String name, Class<? extends RuleEntity> clazz) {
        this.name = name;
        this.clazz = clazz;
    }

    public String getName() {
        return name;
    }

    @SuppressWarnings("unchecked")
    public <T extends RuleEntity> Class<T> getClazz() {
        return (Class<T>) clazz;
    }

    public static Optional<RuleType> of(String name) {
        if (StringUtils.isEmpty(name)) {
            return Optional.empty();
        }
        return Arrays.stream(RuleType.values())
                .filter(ruleType -> name.equals(ruleType.getName())).findFirst();
    }


}
