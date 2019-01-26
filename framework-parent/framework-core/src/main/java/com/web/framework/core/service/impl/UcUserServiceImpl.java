package com.web.framework.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web.framework.common.annotation.Log;
import com.web.framework.common.annotation.UserOpreationLog;
import com.web.framework.common.constants.FrameworkConstants;
import com.web.framework.common.constants.UserOperationConstants;
import com.web.framework.common.utils.R;
import com.web.framework.common.utils.R.Data;
import com.web.framework.common.utils.S;
import com.web.framework.common.utils.VerifyUtil;
import com.web.framework.common.validator.EmailValidator;
import com.web.framework.core.entity.UcUser;
import com.web.framework.core.entity.UcUserCellphone;
import com.web.framework.core.entity.UcUserEmail;
import com.web.framework.core.mapper.UcUserCellphoneMapper;
import com.web.framework.core.mapper.UcUserEmailMapper;
import com.web.framework.core.mapper.UcUserMapper;
import com.web.framework.core.model.dto.UserDTO;
import com.web.framework.core.model.vo.UserVO;
import com.web.framework.core.service.UcUserService;

/**
 * <p>
 * UC用户基本信息 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2018-10-31
 */
@Service
@Transactional
public class UcUserServiceImpl extends ServiceImpl<UcUserMapper, UcUser> implements UcUserService {

  @Autowired
  private UcUserMapper ucUserMapper;

  @Autowired
  private UcUserCellphoneMapper ucUserCellphoneMapper;

  @Autowired
  private UcUserEmailMapper ucUserEmailMapper;

  @Override
  @Cacheable(value = "UcUser", key = "#ucName")
  public UcUser findByUcName(String ucName) {
    List<UcUser> ucUsers =
        ucUserMapper.selectList(new QueryWrapper<UcUser>().eq("uc_name", ucName).eq("status", 1));

    if (ucUsers.size() != 1) {
      return null;
    }

    return ucUsers.get(0);
  }

  @Override
  @UserOpreationLog(code = UserOperationConstants.U01)
  public List<UserDTO> list(UserVO vo) {
    List<UserDTO> reList = new ArrayList<>(1);
    if (StringUtils.isNotBlank(vo.getCellphone())) {
      List<UcUserCellphone> cellphones = ucUserCellphoneMapper
          .selectList(new QueryWrapper<UcUserCellphone>().eq("cellphone", vo.getCellphone()));
      for (UcUserCellphone item : cellphones) {
        UcUser user = new UcUser();
        user.setId(item.getUserId());
        reList.addAll(listUser(user));
      }
      return reList;
    }

    if (StringUtils.isNotBlank(vo.getEmail())) {
      List<UcUserEmail> emails =
          ucUserEmailMapper.selectList(new QueryWrapper<UcUserEmail>().eq("email", vo.getEmail()));
      for (UcUserEmail item : emails) {
        UcUser user = new UcUser();
        user.setId(item.getUserId());
        reList.addAll(listUser(user));
      }
      return reList;
    }

    UcUser ucUser = new UcUser();

    ucUser.setIdentityCard(vo.getIdentityCard());
    ucUser.setTrueName(vo.getTrueName());
    ucUser.setNickName(vo.getNickName());
    ucUser.setUcName(vo.getUcName());
    return listUser(ucUser);
  }

  private List<UserDTO> listUser(UcUser ucUser) {
    List<UserDTO> userDTOs;
    List<UcUser> users = ucUserMapper.selectList(new QueryWrapper<UcUser>()
        .eq(StringUtils.isNotBlank(ucUser.getUcName()), "upper_uc_name",
            StringUtils.upperCase(ucUser.getUcName()))
        .eq(StringUtils.isNotBlank(ucUser.getIdentityCard()), "identity_card",
            ucUser.getIdentityCard())
        .eq(StringUtils.isNotBlank(ucUser.getTrueName()), "true_name", ucUser.getTrueName())
        .eq(StringUtils.isNotBlank(ucUser.getNickName()), "nick_name", ucUser.getNickName())
        .eq(ucUser.getId() != null, "id", ucUser.getId()));

    userDTOs = new ArrayList<>(users.size());
    for (UcUser item : users) {
      UserDTO dto = new UserDTO(item);

      dto.setEmails(ucUserEmailMapper
          .selectList(new QueryWrapper<UcUserEmail>().eq("user_id", item.getId())));
      dto.setCellphones(ucUserCellphoneMapper
          .selectList(new QueryWrapper<UcUserCellphone>().eq("user_id", item.getId())));
      userDTOs.add(dto);
    }

    return userDTOs;
  }

  @Override
  public Data register(UserVO vo) {
    R.Data checkResult = check(vo);
    if (S.S_SUCCESS.getCode() != checkResult.getCode()) {
      return checkResult;
    }

    UcUser ucUser = new UcUser();
    ucUser.setIdentityCard(vo.getIdentityCard());
    ucUser.setUcName(vo.getUcName());
    ucUser.setUpperUcName(StringUtils.upperCase(vo.getUcName()));
    ucUser.setNickName(vo.getNickName());
    ucUser.setTrueName(vo.getTrueName());
    ucUser.setStatus(1);
    ucUserMapper.insert(ucUser);

    if (StringUtils.isNotBlank(vo.getCellphone()) || StringUtils.isNotBlank(vo.getEmail())) {
      UcUser ucUserOne = ucUserMapper.selectOne(
          new QueryWrapper<UcUser>().eq("upper_uc_name", StringUtils.upperCase(vo.getUcName())));

      if (StringUtils.isNotBlank(vo.getCellphone())) {
        UcUserCellphone ucUserCellphone = new UcUserCellphone();
        ucUserCellphone.setUserId(ucUserOne.getId());
        ucUserCellphone.setCellphone(vo.getCellphone());
        ucUserCellphone.setStatus(1);
        ucUserCellphone.setUpdateUser(FrameworkConstants.APP_CODE);
        ucUserCellphoneMapper.insert(ucUserCellphone);
      }
      if (StringUtils.isNotBlank(vo.getEmail())) {
        UcUserEmail ucUserEmail = new UcUserEmail();
        ucUserEmail.setUserId(ucUserOne.getId());
        ucUserEmail.setStatus(1);
        ucUserEmail.setEmail(vo.getEmail());
        ucUserEmail.setUpdateUser(FrameworkConstants.APP_CODE);
        ucUserEmailMapper.insert(ucUserEmail);
      }

    }

    return R.success(ucUser);
  }

  @Override
  @Transactional(readOnly = true)
  public R.Data check(UserVO vo) {

    if (StringUtils.isBlank(vo.getUcName()) && StringUtils.isBlank(vo.getCellphone())
        && StringUtils.isBlank(vo.getIdentityCard()) && StringUtils.isBlank(vo.getEmail())) {
      return R.data(S.S_3004);
    }

    QueryWrapper ucUserQuery =
        new QueryWrapper<UcUser>().eq("upper_uc_name", StringUtils.upperCase(vo.getUcName()));
    if (StringUtils.isNotBlank(vo.getIdentityCard())) {
      ((QueryWrapper) ucUserQuery.or()).eq("identity_card", vo.getIdentityCard());
    }
    List<UcUser> ucUsers = ucUserMapper.selectList(ucUserQuery);
    if (ucUsers.size() > 0) {
      return R.data(S.S_3005);
    }

    if (StringUtils.isNotBlank(vo.getCellphone())) {

      if (!VerifyUtil.cellphone(vo.getCellphone())) {
        return R.data(S.S_3008);
      }

      List<UcUserCellphone> ucUserCellphones =
          ucUserCellphoneMapper.selectList(new QueryWrapper<UcUserCellphone>()
              .eq(StringUtils.isNotBlank(vo.getCellphone()), "cellphone", vo.getCellphone())
              .eq("status", 1));
      if (ucUserCellphones.size() > 0) {
        return R.data(S.S_3006);
      }

    }

    if (StringUtils.isNotBlank(vo.getEmail())) {

      if (!EmailValidator.verifyEmail(vo.getEmail())) {
        return R.data(S.S_3009);
      }

      List<UcUserEmail> ucUserEmails = ucUserEmailMapper
          .selectList(new QueryWrapper<UcUserEmail>().eq("email", vo.getEmail()).eq("status", 1));
      if (ucUserEmails.size() > 0) {
        return R.data(S.S_3007);
      }
    }

    return null;
  }

}
