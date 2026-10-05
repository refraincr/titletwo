package com.uunnm.titletwo.business.auth.controller;

import com.uunnm.titletwo.business.auth.bo.UserRegisterBO;
import com.uunnm.titletwo.business.auth.bo.UserLoginBO;
import com.uunnm.titletwo.business.auth.service.UnitService;
import com.uunnm.titletwo.business.auth.service.UserService;
import com.uunnm.titletwo.business.auth.vo.UserInfoVO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@AllArgsConstructor
public class AuthController {
    private UserService userService;
    private UnitService unitService;

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

    @GetMapping("/auth/units")
    public List<String> getUnits(@RequestParam String name) {
        return unitService.query(name);
    }
}
