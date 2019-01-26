package com.web.framework.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 角色与用户
 * </p>
 *
 * @author ${author}
 * @since 2018-12-13
 */
@TableName("uc_role_user")
public class UcRoleUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    @TableId("role_id")
    private Integer roleId;

    /**
     * uid
     */
    @TableField("user_id")
    private Long userId;


    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UcRoleUser{" +
        ", roleId=" + roleId +
        ", userId=" + userId +
        "}";
    }
}
