package com.web.framework.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 用户注册手机
 * </p>
 *
 * @author ${author}
 * @since 2018-12-13
 */
@TableName("uc_user_cellphone")
public class UcUserCellphone implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * uid
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 手机号码
     */
    private String cellphone;

    /**
     * 状态,1:正常,0:禁用
     */
    private Integer status;

    /**
     * 最后修改时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * uid
     */
    @TableField("update_user")
    private String updateUser;


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

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    @Override
    public String toString() {
        return "UcUserCellphone{" +
        ", id=" + id +
        ", userId=" + userId +
        ", cellphone=" + cellphone +
        ", status=" + status +
        ", updateTime=" + updateTime +
        ", updateUser=" + updateUser +
        "}";
    }
}
