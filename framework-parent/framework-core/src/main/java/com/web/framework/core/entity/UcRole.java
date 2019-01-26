package com.web.framework.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 角色
 * </p>
 *
 * @author ${author}
 * @since 2018-12-13
 */
@TableName("uc_role")
public class UcRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 子分类ID
     */
    @TableField("child_ids")
    private String childIds;

    /**
     * 父角色ID
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 角色名字
     */
    private String name;

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

    public String getChildIds() {
        return childIds;
    }

    public void setChildIds(String childIds) {
        this.childIds = childIds;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return "UcRole{" +
        ", id=" + id +
        ", childIds=" + childIds +
        ", parentId=" + parentId +
        ", name=" + name +
        ", status=" + status +
        ", updateTime=" + updateTime +
        ", updateUser=" + updateUser +
        "}";
    }
}
