package com.uunnm.titletwo.business.auth.controller;

import com.uunnm.titletwo.business.auth.bo.UserRegisterBO;
import com.uunnm.titletwo.business.auth.bo.UserLoginBO;
import com.uunnm.titletwo.business.auth.service.UserService;
import com.uunnm.titletwo.business.auth.vo.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("login")
    public String login(@RequestBody UserLoginBO userLoginBO) {
        return userService.login(userLoginBO);
    }

    @PostMapping("register")
    public void register(@RequestBody UserRegisterBO userRegisterBO) {
        userService.register(userRegisterBO);
    }

    @GetMapping("/user/info")
    public UserInfoVO getUserInfo() {
        return userService.getUserInfo();
    }
}
