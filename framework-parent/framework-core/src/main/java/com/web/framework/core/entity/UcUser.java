package com.web.framework.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * UC用户基本信息
 * </p>
 *
 * @author ${author}
 * @since 2018-12-13
 */
@TableName("uc_user")
public class UcUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * uid
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * uc_name
     */
    @TableField("uc_name")
    private String ucName;

    /**
     * 大写的uc_name
     */
    @TableField("upper_uc_name")
    private String upperUcName;

    /**
     * 身份证号码
     */
    @TableField("identity_card")
    private String identityCard;

    /**
     * 真实姓名
     */
    @TableField("true_name")
    private String trueName;

    /**
     * 昵称
     */
    @TableField("nick_name")
    private String nickName;

    /**
     * 用户状态,1:正常,0:禁用,2:销户
     */
    private Integer status;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUcName() {
        return ucName;
    }

    public void setUcName(String ucName) {
        this.ucName = ucName;
    }

    public String getUpperUcName() {
        return upperUcName;
    }

    public void setUpperUcName(String upperUcName) {
        this.upperUcName = upperUcName;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "UcUser{" +
        ", id=" + id +
        ", ucName=" + ucName +
        ", upperUcName=" + upperUcName +
        ", identityCard=" + identityCard +
        ", trueName=" + trueName +
        ", nickName=" + nickName +
        ", status=" + status +
        "}";
    }
}
