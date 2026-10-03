package com.uunnm.titletwo.business.record.bo;

import com.uunnm.titletwo.common.entity.PageBO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;


@Data
@EqualsAndHashCode(callSuper = true)
public class RecordQueryBO extends PageBO {
    // 事件发生的时间区间
    private LocalDateTime occurredStart;
    private LocalDateTime occurredEnd;

    // 事件上传的时间区间
    private LocalDateTime gmtCreateStart;
    private LocalDateTime gmtCreateEnd;

    // 当事人姓名
    private String partyName;

    // 部门
    private String department;

    // 纠纷类型
    private String disputeType;

    // 关键词
    private String keyword;
}
