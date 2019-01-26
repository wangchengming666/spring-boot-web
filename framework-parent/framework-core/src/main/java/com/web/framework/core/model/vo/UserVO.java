package com.web.framework.core.model.vo;

import java.io.Serializable;
import com.web.framework.common.model.ObjectBase;

/**
 * 用户登录VO
 * 
 * @author cm_wang
 *
 */
public class UserVO extends ObjectBase implements Serializable {
  /**
   * uc_name
   */
  private String ucName;

  /**
   * 身份证号码
   */
  private String identityCard;
  /**
   * 手机号码
   */
  private String cellphone;
  /**
   * 邮箱地址
   */
  private String email;

  /**
   * 真实姓名
   */
  private String trueName;

  /**
   * 昵称
   */
  private String nickName;

  public String getUcName() {
    return ucName;
  }

  public void setUcName(String ucName) {
    this.ucName = ucName;
  }

  public String getIdentityCard() {
    return identityCard;
  }

  public void setIdentityCard(String identityCard) {
    this.identityCard = identityCard;
  }

  public String getCellphone() {
    return cellphone;
  }

  public void setCellphone(String cellphone) {
    this.cellphone = cellphone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
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
}
