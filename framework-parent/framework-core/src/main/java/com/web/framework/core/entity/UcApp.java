package com.web.framework.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 第三方应用（内部系统，外部系统）,key,secret通过SELECT MD5(RAND()*10000)
 * </p>
 *
 * @author ${author}
 * @since 2018-12-13
 */
@TableName("uc_app")
public class UcApp implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * APP key
     */
    @TableId("app_key")
    private String appKey;

    /**
     * APP code(内部系统方便使用)
     */
    @TableField("app_code")
    private String appCode;

    /**
     * APP secret
     */
    @TableField("app_secret")
    private String appSecret;

    /**
     * 系统名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 过期时间
     */
    @TableField("expiry_minutes")
    private Integer expiryMinutes;

    /**
     * uid
     */
    @TableField("update_user")
    private String updateUser;

    /**
     * 最后修改时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;


    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getExpiryMinutes() {
        return expiryMinutes;
    }

    public void setExpiryMinutes(Integer expiryMinutes) {
        this.expiryMinutes = expiryMinutes;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "UcApp{" +
        ", appKey=" + appKey +
        ", appCode=" + appCode +
        ", appSecret=" + appSecret +
        ", name=" + name +
        ", description=" + description +
        ", expiryMinutes=" + expiryMinutes +
        ", updateUser=" + updateUser +
        ", updateTime=" + updateTime +
        "}";
    }
}
