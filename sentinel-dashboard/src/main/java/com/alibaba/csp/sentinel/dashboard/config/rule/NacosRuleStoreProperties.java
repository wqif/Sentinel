package com.alibaba.csp.sentinel.dashboard.config.rule;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Will
 * @since 2.0.0
 */
@ConfigurationProperties("sentinel-plus.rule.store.nacos")
public class NacosRuleStoreProperties {

    private String namespace;

    private String serverAddr;

    private String group = "SENTINEL_GROUP";

    private String username;

    private String password;

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getServerAddr() {
        return serverAddr;
    }

    public void setServerAddr(String serverAddr) {
        this.serverAddr = serverAddr;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
