package com.alibaba.csp.sentinel.dashboard.idgenerator;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Will
 * @since 2.0.0
 */
public class AtomicIdGenerator implements IdGenerator {

    private final AtomicLong ids;

    public AtomicIdGenerator() {
        ids = new AtomicLong(0);
    }

    @Override
    public long nextId() {
        return ids.incrementAndGet();
    }
}
