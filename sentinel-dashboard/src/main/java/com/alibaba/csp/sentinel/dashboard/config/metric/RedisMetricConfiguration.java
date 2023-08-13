package com.alibaba.csp.sentinel.dashboard.config.metric;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.MetricEntity;
import com.alibaba.csp.sentinel.dashboard.repository.metric.MetricsRepository;
import com.alibaba.csp.sentinel.dashboard.repository.metric.RedisMetricsRepository;
import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @author Will
 * @since 2.0.0
 */
@AutoConfigureAfter(RedisAutoConfiguration.class)
@ConditionalOnProperty(prefix = "sentinel-plus.metric.store", name = "type", havingValue = "redis")
public class RedisMetricConfiguration {

    @Bean
    public RedisTemplate<String, MetricEntity> metricRedisTemplate(final RedisConnectionFactory factory) {
        return this.build(factory, MetricEntity.class);
    }

    @Bean
    public MetricsRepository<MetricEntity> metricStore() {
        return new RedisMetricsRepository();
    }


    private <T> RedisTemplate<String, T> build(final RedisConnectionFactory factory, Class<T> type) {
        final FastJsonRedisSerializer<T> valueSerializer = new FastJsonRedisSerializer<>(type);

        RedisTemplate<String, T> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setDefaultSerializer(RedisSerializer.string());

        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setValueSerializer(valueSerializer);

        redisTemplate.setStringSerializer(RedisSerializer.string());

        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        redisTemplate.setHashValueSerializer(valueSerializer);

        return redisTemplate;
    }
}
