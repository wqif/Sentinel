package com.alibaba.csp.sentinel.dashboard.datasource.entity.rule;

import com.alibaba.csp.sentinel.slots.block.Rule;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowClusterConfig;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowItem;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.util.AssertUtil;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.util.*;

/**
 * Custom authority rule entity class.
 * There is a difference between the official {@link ParamFlowRuleEntity} and {@link ParamFlowRule} structure.
 *
 * @author Will
 * @since 2.0.0
 */
public class CustomParamFlowRuleEntity implements RuleEntity {
    private Long id;
    private String app;
    private String ip;
    private Integer port;
    private String limitApp;
    private String resource;
    private Date gmtCreate;
    private Date gmtModified;

    /**
     * The threshold type of flow control (0: thread count, 1: QPS).
     */
    private int grade = RuleConstant.FLOW_GRADE_QPS;

    /**
     * Parameter index.
     */
    private Integer paramIdx;

    /**
     * The threshold count.
     */
    private double count;

    /**
     * Traffic shaping behavior (since 1.6.0).
     */
    private int controlBehavior = RuleConstant.CONTROL_BEHAVIOR_DEFAULT;

    private int maxQueueingTimeMs = 0;
    private int burstCount = 0;
    private long durationInSec = 1;

    /**
     * Original exclusion items of parameters.
     */
    private List<ParamFlowItem> paramFlowItemList = new ArrayList<ParamFlowItem>();

    /**
     * Parsed exclusion items of parameters. Only for internal use.
     */
    private Map<Object, Integer> hotItems = new HashMap<Object, Integer>();

    /**
     * Indicating whether the rule is for cluster mode.
     */
    private boolean clusterMode = false;
    /**
     * Cluster mode specific config for parameter flow rule.
     */
    private ParamFlowClusterConfig clusterConfig;

    public static CustomParamFlowRuleEntity fromParamFlowRule(String app, String ip, Integer port, ParamFlowRule rule) {
        AssertUtil.notNull(rule, "Param flow rule should not be null");
        CustomParamFlowRuleEntity entity = new CustomParamFlowRuleEntity();
        entity.setApp(app);
        entity.setIp(ip);
        entity.setPort(port);
        entity.setId(rule.getId());
        entity.setLimitApp(rule.getLimitApp());
        entity.setResource(rule.getResource());
        entity.setGrade(rule.getGrade());
        entity.setParamIdx(rule.getParamIdx());
        entity.setCount(rule.getCount());
        entity.setControlBehavior(rule.getControlBehavior());
        entity.setMaxQueueingTimeMs(rule.getMaxQueueingTimeMs());
        entity.setBurstCount(rule.getBurstCount());
        entity.setDurationInSec(rule.getDurationInSec());
        entity.setParamFlowItemList(rule.getParamFlowItemList());
        entity.setClusterMode(rule.isClusterMode());
        entity.setClusterConfig(rule.getClusterConfig());
        return entity;
    }

    public static CustomParamFlowRuleEntity fromParamFlowRule(ParamFlowRuleEntity paramFlowRuleEntity) {
        CustomParamFlowRuleEntity entity = new CustomParamFlowRuleEntity();
        entity.setApp(paramFlowRuleEntity.getApp());
        entity.setIp(paramFlowRuleEntity.getIp());
        entity.setPort(paramFlowRuleEntity.getPort());
        entity.setId(paramFlowRuleEntity.getId());
        entity.setLimitApp(paramFlowRuleEntity.getLimitApp());
        entity.setResource(paramFlowRuleEntity.getResource());
        entity.setGrade(paramFlowRuleEntity.getGrade());
        entity.setParamIdx(paramFlowRuleEntity.getParamIdx());
        entity.setCount(paramFlowRuleEntity.getCount());
        entity.setControlBehavior(paramFlowRuleEntity.getControlBehavior());
        entity.setMaxQueueingTimeMs(paramFlowRuleEntity.getMaxQueueingTimeMs());
        entity.setBurstCount(paramFlowRuleEntity.getBurstCount());
        entity.setDurationInSec(paramFlowRuleEntity.getDurationInSec());
        entity.setParamFlowItemList(paramFlowRuleEntity.getParamFlowItemList());
        entity.setClusterMode(paramFlowRuleEntity.isClusterMode());
        entity.setClusterConfig(paramFlowRuleEntity.getClusterConfig());
        return entity;
    }

    @JsonSerialize(using = ToStringSerializer.class)
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    @Override
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getLimitApp() {
        return limitApp;
    }

    public void setLimitApp(String limitApp) {
        this.limitApp = limitApp;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    @Override
    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public Integer getParamIdx() {
        return paramIdx;
    }

    public void setParamIdx(Integer paramIdx) {
        this.paramIdx = paramIdx;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public int getControlBehavior() {
        return controlBehavior;
    }

    public void setControlBehavior(int controlBehavior) {
        this.controlBehavior = controlBehavior;
    }

    public int getMaxQueueingTimeMs() {
        return maxQueueingTimeMs;
    }

    public void setMaxQueueingTimeMs(int maxQueueingTimeMs) {
        this.maxQueueingTimeMs = maxQueueingTimeMs;
    }

    public int getBurstCount() {
        return burstCount;
    }

    public void setBurstCount(int burstCount) {
        this.burstCount = burstCount;
    }

    public long getDurationInSec() {
        return durationInSec;
    }

    public void setDurationInSec(long durationInSec) {
        this.durationInSec = durationInSec;
    }

    public List<ParamFlowItem> getParamFlowItemList() {
        return paramFlowItemList;
    }

    public void setParamFlowItemList(List<ParamFlowItem> paramFlowItemList) {
        this.paramFlowItemList = paramFlowItemList;
    }

    public Map<Object, Integer> getHotItems() {
        return hotItems;
    }

    public void setHotItems(Map<Object, Integer> hotItems) {
        this.hotItems = hotItems;
    }

    public boolean isClusterMode() {
        return clusterMode;
    }

    public void setClusterMode(boolean clusterMode) {
        this.clusterMode = clusterMode;
    }

    public ParamFlowClusterConfig getClusterConfig() {
        return clusterConfig;
    }

    public void setClusterConfig(ParamFlowClusterConfig clusterConfig) {
        this.clusterConfig = clusterConfig;
    }

    @Override
    public Rule toRule() {
        return this.toParamFlowRule();
    }

    public ParamFlowRule toParamFlowRule() {
        ParamFlowRule paramFlowRule = new ParamFlowRule();
        paramFlowRule.setId(id);
        paramFlowRule.setLimitApp(limitApp);
        paramFlowRule.setResource(resource);
        paramFlowRule.setGrade(grade);
        paramFlowRule.setParamIdx(paramIdx);
        paramFlowRule.setCount(count);
        paramFlowRule.setControlBehavior(controlBehavior);
        paramFlowRule.setMaxQueueingTimeMs(maxQueueingTimeMs);
        paramFlowRule.setBurstCount(burstCount);
        paramFlowRule.setDurationInSec(durationInSec);
        paramFlowRule.setParamFlowItemList(paramFlowItemList);
        paramFlowRule.setClusterMode(clusterMode);
        paramFlowRule.setClusterConfig(clusterConfig);
        return paramFlowRule;
    }

    public ParamFlowRuleEntity toParamFlowRuleEntity() {
        ParamFlowRuleEntity paramFlowRuleEntity = ParamFlowRuleEntity.fromParamFlowRule(app, ip, port, this.toParamFlowRule());
        paramFlowRuleEntity.setId(id);
        paramFlowRuleEntity.setGmtCreate(gmtCreate);
        paramFlowRuleEntity.setGmtModified(gmtModified);
        return paramFlowRuleEntity;
    }
}
