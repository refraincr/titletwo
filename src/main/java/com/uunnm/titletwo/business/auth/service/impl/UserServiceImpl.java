package com.uunnm.titletwo.business.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.uunnm.titletwo.business.auth.bo.UserLoginBO;
import com.uunnm.titletwo.business.auth.bo.UserRegisterBO;
import com.uunnm.titletwo.business.auth.entity.User;
import com.uunnm.titletwo.business.auth.mapper.UserMapper;
import com.uunnm.titletwo.business.auth.service.JwtService;
import com.uunnm.titletwo.business.auth.service.UserService;
import com.uunnm.titletwo.business.auth.vo.UserInfoVO;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Override
    public void register(UserRegisterBO userRegisterBO) {
        User u =  new User();
        BeanUtils.copyProperties(userRegisterBO,u);
        u.setPassword(passwordEncoder.encode(userRegisterBO.getPassword()));
        save(u);
    }

    @Override
    public String login(UserLoginBO userLoginBO) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginBO.getUsername(),
                        userLoginBO.getPassword()
                )
        );
        if (authenticate.isAuthenticated()) {
            return jwtService.getToken(userLoginBO.getUsername());
        }
        return "fail";
    }

    @Override
    public UserInfoVO getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = null;
        if (authentication != null) {
            username = authentication.getName();
        }

        if (username != null) {
            LambdaQueryWrapper<User> lambdaQueryWrapper = Wrappers.lambdaQuery();
            lambdaQueryWrapper.eq(User::getUsername, username);
            User user = getOne(lambdaQueryWrapper);

            UserInfoVO userInfoVO = new UserInfoVO();
            BeanUtils.copyProperties(user, userInfoVO);
            return userInfoVO;
        }

        return null;
    }
}
