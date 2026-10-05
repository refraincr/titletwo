package com.uunnm.titletwo.business.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("`t_user`")
public class User {
    @TableId(value="id",type= IdType.ASSIGN_ID)  // mybatisplus 自动填充 id
    private Long id;

    private String username;
    private String password;
    private String role;
    private String unit;
}
