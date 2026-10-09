package com.uunnm.titletwo.business.person_profile.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.uunnm.titletwo.common.entity.CommonEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_personal_record")
public class PersonProfile extends CommonEntity {

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

    /**
     * 最近关联事件发生时间
     */
    private LocalDateTime lastOccurredAt;

    /**
     * 关键词
     */
    private String keywords;

    /**
     * 事件风险等级
     */
    private String eventRiskLevel;
}
