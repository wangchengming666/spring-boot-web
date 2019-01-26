package com.web.framework.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 应用客户端
 * </p>
 *
 * @author ${author}
 * @since 2018-12-13
 */
@TableName("uc_app_user")
public class UcAppUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * APP key
     */
    @TableField("app_key")
    private String appKey;

    /**
     * 绑定用户
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 版本
     */
    @TableField("client_version")
    private String clientVersion;

    /**
     * 上次登录时间
     */
    @TableField("last_login_date")
    private LocalDateTime lastLoginDate;

    /**
     * 上次登录IP
     */
    @TableField("last_login_ip")
    private String lastLoginIp;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 是否禁用
     */
    private Boolean disabled;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }

    public LocalDateTime getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(LocalDateTime lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    @Override
    public String toString() {
        return "UcAppUser{" +
        ", id=" + id +
        ", appKey=" + appKey +
        ", userId=" + userId +
        ", clientVersion=" + clientVersion +
        ", lastLoginDate=" + lastLoginDate +
        ", lastLoginIp=" + lastLoginIp +
        ", createTime=" + createTime +
        ", disabled=" + disabled +
        "}";
    }
}
