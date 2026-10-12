package com.uunnm.titletwo.business.risk.vo;

import lombok.Data;

@Data
public class RiskVO {
    /** 事件命中的关键词（JSON 数组字符串，null 表示未命中） */
    private String keywords;

    /** 是否为特殊账号：0-否，1-是 */
    private Boolean isSpecialAccount;

    /** 事件当事人的关联事件次数 */
    private Integer partyEventCount;

}
