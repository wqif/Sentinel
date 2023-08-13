package com.alibaba.csp.sentinel.dashboard.datasource.entity.rule;

import com.alibaba.csp.sentinel.slots.block.Rule;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.util.AssertUtil;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.util.Date;

/**
 * Custom authority rule entity class.
 * There is a difference between the official {@link AuthorityRuleEntity} and {@link AuthorityRule} structure.
 *
 * @author Will
 * @since 2.0.0
 */
public class CustomAuthorityRuleEntity implements RuleEntity {
    private Long id;
    private String app;
    private String ip;
    private Integer port;
    private String limitApp;
    private String resource;
    private Date gmtCreate;
    private Date gmtModified;
    private int strategy;

    public static CustomAuthorityRuleEntity fromAuthorityRule(String app, String ip, Integer port, AuthorityRule rule) {
        AssertUtil.notNull(rule, "Authority rule should not be null");
        CustomAuthorityRuleEntity entity = new CustomAuthorityRuleEntity();
        entity.setApp(app);
        entity.setIp(ip);
        entity.setPort(port);
        entity.setId(rule.getId());
        entity.setLimitApp(rule.getLimitApp());
        entity.setResource(rule.getResource());
        entity.setStrategy(rule.getStrategy());
        return entity;
    }

    public static CustomAuthorityRuleEntity fromAuthorityRuleEntity(AuthorityRuleEntity authorityRuleEntity) {
        CustomAuthorityRuleEntity entity = new CustomAuthorityRuleEntity();
        entity.setId(authorityRuleEntity.getId());
        entity.setApp(authorityRuleEntity.getApp());
        entity.setIp(authorityRuleEntity.getIp());
        entity.setPort(authorityRuleEntity.getPort());
        entity.setLimitApp(authorityRuleEntity.getLimitApp());
        entity.setResource(authorityRuleEntity.getResource());
        entity.setGmtCreate(authorityRuleEntity.getGmtCreate());
        entity.setGmtModified(authorityRuleEntity.getGmtModified());
        entity.setStrategy(authorityRuleEntity.getStrategy());
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

    public int getStrategy() {
        return strategy;
    }

    public void setStrategy(int strategy) {
        this.strategy = strategy;
    }

    @Override
    public Rule toRule() {
        return toAuthorityRule();
    }

    private AuthorityRule toAuthorityRule() {
        AuthorityRule authorityRule = new AuthorityRule();
        authorityRule.setId(id);
        authorityRule.setStrategy(strategy);
        authorityRule.setResource(resource);
        authorityRule.setLimitApp(limitApp);
        return authorityRule;
    }

    public AuthorityRuleEntity toAuthorityRuleEntity() {
        AuthorityRuleEntity authorityRuleEntity = AuthorityRuleEntity.fromAuthorityRule(app, ip, port, this.toAuthorityRule());
        authorityRuleEntity.setId(id);
        authorityRuleEntity.setGmtCreate(gmtCreate);
        authorityRuleEntity.setGmtCreate(gmtModified);
        return authorityRuleEntity;
    }
}
