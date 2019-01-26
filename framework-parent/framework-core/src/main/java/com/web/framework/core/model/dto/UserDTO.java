package com.web.framework.core.model.dto;

import java.util.List;
import com.web.framework.core.entity.UcUser;
import com.web.framework.core.entity.UcUserCellphone;
import com.web.framework.core.entity.UcUserEmail;

public class UserDTO extends UcUser {

  private List<UcUserCellphone> cellphones;
  private List<UcUserEmail> emails;


  public UserDTO() {}

  public UserDTO(UcUser ucUser) {
    setId(ucUser.getId());
    setIdentityCard(ucUser.getIdentityCard());
    setTrueName(ucUser.getTrueName());
    setNickName(ucUser.getNickName());
    setUcName(ucUser.getUcName());
    setStatus(ucUser.getStatus());

  }

  public List<UcUserCellphone> getCellphones() {
    return cellphones;
  }

  public void setCellphones(List<UcUserCellphone> cellphones) {
    this.cellphones = cellphones;
  }

  public List<UcUserEmail> getEmails() {
    return emails;
  }

  public void setEmails(List<UcUserEmail> emails) {
    this.emails = emails;
  }



}
