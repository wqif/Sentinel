package com.alibaba.csp.sentinel.dashboard.idgenerator;

/**
 * @author Will
 * @since 2.0.0
 */
public class SnowflakeIdGenerator implements IdGenerator {

    private final Snowflake SF;

    public SnowflakeIdGenerator() {
        SF = new Snowflake();
    }

    @Override
    public long nextId() {
        return SF.nextId();
    }

}
