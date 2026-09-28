package com.uunnm.titletwo.business.auth.bo;

import lombok.Data;

@Data
public class UserRegisterBO {
    private String username;
    private String password;
    private int role;
}
