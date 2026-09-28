package com.uunnm.titletwo.business.auth.service;

import com.baomidou.mybatisplus.spring.service.IService;
import com.uunnm.titletwo.business.auth.bo.UserLoginBO;
import com.uunnm.titletwo.business.auth.bo.UserRegisterBO;
import com.uunnm.titletwo.business.auth.entity.User;
import com.uunnm.titletwo.business.auth.vo.UserInfoVO;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends IService<User> {
    void register(UserRegisterBO userRegisterBO);
    String login(UserLoginBO userLoginBO);
    UserInfoVO getUserInfo();
}
