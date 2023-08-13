package com.alibaba.csp.sentinel.dashboard.idgenerator;

import com.alibaba.csp.sentinel.util.function.Supplier;

/**
 * @author Will
 * @since 2.0.0
 */
public enum IdGeneratorType {

    SNOWFLAKE(SnowflakeIdGenerator::new),
    ATOMIC(AtomicIdGenerator::new),
    ;


    private final Supplier<IdGenerator> constructor;

    IdGeneratorType(final Supplier<IdGenerator> constructor) {
        this.constructor = constructor;
    }

    public Supplier<IdGenerator> getConstructor() {
        return constructor;
    }
}
