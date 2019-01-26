package com.web.framework.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 操作日志
 * </p>
 *
 * @author ${author}
 * @since 2018-12-18
 */
@TableName("log_operation")
public class LogOperation implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * IP
     */
    private String ip;

    /**
     * uc_name
     */
    @TableField("uc_name")
    private String ucName;

    /**
     * 代码
     */
    @TableField("operate_code")
    private String operateCode;

    /**
     * 操作
     */
    @TableField("operate_value")
    private String operateValue;

    /**
     * 创建日期
     */
    @TableField("create_date")
    private LocalDateTime createDate;

    /**
     * 内容
     */
    private String content;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUcName() {
        return ucName;
    }

    public void setUcName(String ucName) {
        this.ucName = ucName;
    }

    public String getOperateCode() {
        return operateCode;
    }

    public void setOperateCode(String operateCode) {
        this.operateCode = operateCode;
    }

    public String getOperateValue() {
        return operateValue;
    }

    public void setOperateValue(String operateValue) {
        this.operateValue = operateValue;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "LogOperation{" +
        ", id=" + id +
        ", userId=" + userId +
        ", ip=" + ip +
        ", ucName=" + ucName +
        ", operateCode=" + operateCode +
        ", operateValue=" + operateValue +
        ", createDate=" + createDate +
        ", content=" + content +
        "}";
    }
}
