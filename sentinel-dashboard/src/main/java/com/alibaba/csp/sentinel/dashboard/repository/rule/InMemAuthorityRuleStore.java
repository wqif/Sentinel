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
package com.alibaba.csp.sentinel.dashboard.repository.rule;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.CustomAuthorityRuleEntity;
import com.alibaba.csp.sentinel.dashboard.idgenerator.IdGenerator;
import com.alibaba.csp.sentinel.dashboard.idgenerator.IdGeneratorFactory;
import com.alibaba.csp.sentinel.dashboard.idgenerator.IdGeneratorType;
import org.springframework.stereotype.Component;

/**
 * In-memory storage for authority rules.
 *
 * @author Eric Zhao
 * @since 0.2.1
 */
@Component
public class InMemAuthorityRuleStore extends InMemoryRuleRepositoryAdapter<CustomAuthorityRuleEntity> {

    private static final IdGenerator ID_GENERATOR = IdGeneratorFactory.create(IdGeneratorType.SNOWFLAKE);

    @Override
    protected long nextId() {
        return ID_GENERATOR.nextId();
    }

}
