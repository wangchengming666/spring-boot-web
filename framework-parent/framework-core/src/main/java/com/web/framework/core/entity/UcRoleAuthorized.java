package com.web.framework.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 角色授权地址
 * </p>
 *
 * @author ${author}
 * @since 2018-12-13
 */
@TableName("uc_role_authorized")
public class UcRoleAuthorized implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    @TableId("role_id")
    private Integer roleId;

    /**
     * 授权地址（菜单与按钮)
     */
    private String url;


    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "UcRoleAuthorized{" +
        ", roleId=" + roleId +
        ", url=" + url +
        "}";
    }
}
