package com.alibaba.csp.sentinel.dashboard.idgenerator;

/**
 * @author Will
 * @since 2.0.0
 */
public class IdGeneratorFactory {

    public static IdGenerator create(IdGeneratorType type) {
        return type.getConstructor().get();
    }

}
