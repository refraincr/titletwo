package com.uunnm.titletwo.business.risk.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
@TableName("tb_risk_calculation_basis")
public class RiskCalculationBasis {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /** 事件命中的关键词（JSON 数组字符串，null 表示未命中） */
    private String keywords;

    /** 是否为特殊账号：0-否，1-是 */
    private Boolean isSpecialAccount;

    /** 事件当事人的关联事件次数 */
    private Integer partyEventCount;

    /** 判断依据所关联的事件 ID */
    private Long recordId;
}
