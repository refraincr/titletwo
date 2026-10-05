package com.uunnm.titletwo.business.auth.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    ADMIN("系统管理人员"),
    ANALYST("数据分析人员"),
    REPORTER("风险上报人员"),
    KEY_PERSON("特殊上报人员");

    private final String name;
}
