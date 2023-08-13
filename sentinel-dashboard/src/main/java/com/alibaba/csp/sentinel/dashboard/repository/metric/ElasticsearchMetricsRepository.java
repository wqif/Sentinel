package com.alibaba.csp.sentinel.dashboard.repository.metric;

import com.alibaba.csp.sentinel.dashboard.config.metric.MetricsStoreProperties;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.MetricEntity;
import com.alibaba.csp.sentinel.util.StringUtil;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Will
 * @since 2.0.0
 */
public class ElasticsearchMetricsRepository implements MetricsRepository<MetricEntity> {
    private static final Logger logger = LoggerFactory.getLogger(ElasticsearchMetricsRepository.class);

    private static final String INDEX_NAME = "sentinel_metrics";
    private final ElasticsearchRestTemplate elasticsearchRestTemplate;

    private final MetricsStoreProperties metricsStoreProperties;

    public ElasticsearchMetricsRepository(ElasticsearchRestTemplate elasticsearchRestTemplate,
                                          MetricsStoreProperties metricsStoreProperties) {
        this.elasticsearchRestTemplate = elasticsearchRestTemplate;
        this.metricsStoreProperties = metricsStoreProperties;
    }

    @Override
    public void save(MetricEntity metric) {
        if (metric == null || StringUtil.isEmpty(metric.getApp())) {
            return;
        }
        try {
            elasticsearchRestTemplate.save(metric, IndexCoordinates.of(INDEX_NAME));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void saveAll(Iterable<MetricEntity> metrics) {
        if (metrics == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("metrics is empty");
            }
        }
        metrics.forEach(this::save);
    }

    @Override
    public List<MetricEntity> queryByAppAndResourceBetween(String app, String resource, long startTime, long endTime) {
        List<MetricEntity> results = new ArrayList<>();
        if (StringUtil.isBlank(app) || StringUtil.isBlank(resource)) {
            return results;
        }

        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.matchPhraseQuery("app", app))
                .must(QueryBuilders.matchPhraseQuery("resource", resource))
                .must(QueryBuilders.rangeQuery("timestamp").gte(startTime).lte(endTime));

        NativeSearchQuery query = new NativeSearchQueryBuilder().withQuery(queryBuilder).build();
        try {
            SearchHits<MetricEntity> hits = elasticsearchRestTemplate.search(query, MetricEntity.class, IndexCoordinates.of(INDEX_NAME));
            results = hits.stream()
                    .map(SearchHit::getContent)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return results;
    }

    @Override
    public List<String> listResourcesOfApp(String app) {
        List<String> results = new ArrayList<>();
        if (StringUtil.isBlank(app)) {
            return results;
        }

        // 获取配置可查询时间段的开始时间
        final Pair<Long, Long> maxLiveTime = this.getMaxLiveTime();
        final long startTime = maxLiveTime.getFirst();
        this.cleanOldMetricsData(app, startTime);

        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.matchPhraseQuery("app", app))
                .must(QueryBuilders.rangeQuery("timestamp").gte(startTime));
        NativeSearchQuery query = new NativeSearchQueryBuilder().withQuery(queryBuilder).build();

        List<MetricEntity> metricEntities;
        try {
            SearchHits<MetricEntity> hits = elasticsearchRestTemplate.search(query, MetricEntity.class, IndexCoordinates.of(INDEX_NAME));
            metricEntities = hits.stream()
                    .map(SearchHit::getContent)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return results;
        }

        Map<String, MetricEntity> resourceCount = new HashMap<>(32);
        for (MetricEntity metricEntity : metricEntities) {
            String resource = metricEntity.getResource();
            if (resourceCount.containsKey(resource)) {
                MetricEntity oldEntity = resourceCount.get(resource);
                oldEntity.addPassQps(metricEntity.getPassQps());
                oldEntity.addRtAndSuccessQps(metricEntity.getRt(), metricEntity.getSuccessQps());
                oldEntity.addBlockQps(metricEntity.getBlockQps());
                oldEntity.addExceptionQps(metricEntity.getExceptionQps());
                oldEntity.addCount(1);
            } else {
                resourceCount.put(resource, MetricEntity.copyOf(metricEntity));
            }
        }
        // Order by last minute b_qps DESC.
        return listResourcesSorted(resourceCount);
    }

    private void cleanOldMetricsData(String app, long timestamp) {
        try {
            BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery()
                    .must(QueryBuilders.matchPhraseQuery("app", app))
                    .must(QueryBuilders.rangeQuery("timestamp").lt(timestamp));
            NativeSearchQuery query = new NativeSearchQueryBuilder().withQuery(queryBuilder).build();
            elasticsearchRestTemplate.delete(query, MetricEntity.class, IndexCoordinates.of(INDEX_NAME));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private Pair<Long, Long> getMaxLiveTime() {
        final long millis = metricsStoreProperties.getMaxLiveTime().toMillis();
        final long endTime = System.currentTimeMillis();
        final long startTime = endTime - millis;
        return Pair.of(startTime, endTime);
    }
}
