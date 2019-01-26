package com.web.framework.core.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.web.framework.common.utils.R;
import com.web.framework.core.entity.UcUser;
import com.web.framework.core.model.dto.UserDTO;
import com.web.framework.core.model.vo.UserVO;

/**
 * <p>
 * UC用户基本信息 服务类
 * </p>
 *
 * @author ${author}
 * @since 2018-10-31
 */
public interface UcUserService extends IService<UcUser> {

  public R.Data register(UserVO vo);

  public List<UserDTO> list(UserVO vo);

  public R.Data check(UserVO vo);

  public UcUser findByUcName(String ucName);

}
