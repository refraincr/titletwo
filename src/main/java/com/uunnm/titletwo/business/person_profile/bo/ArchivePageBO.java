package com.uunnm.titletwo.business.person_profile.bo;

import com.uunnm.titletwo.common.entity.PageBO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ArchivePageBO extends PageBO {
    /**
     * 人员姓名
     */
    private String name;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 身份证号
     */
    private String idCard;
}
