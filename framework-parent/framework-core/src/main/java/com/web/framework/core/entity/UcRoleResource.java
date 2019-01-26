package com.web.framework.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 角色与资源关系
 * </p>
 *
 * @author ${author}
 * @since 2018-12-13
 */
@TableName("uc_role_resource")
public class UcRoleResource implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 资源 ID
     */
    @TableId("resource_id")
    private Long resourceId;

    /**
     * 角色ID
     */
    @TableField("role_id")
    private Integer roleId;


    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "UcRoleResource{" +
        ", resourceId=" + resourceId +
        ", roleId=" + roleId +
        "}";
    }
}
