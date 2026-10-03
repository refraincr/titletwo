package com.uunnm.titletwo.business.record.bo;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RecordEditBO {
    private Long id;

    /**
     * 事件发生时间
     */
    private LocalDateTime occurredAt;

    /**
     * 具体问题
     */
    private String problemDescription;

    /**
     * 当事人姓名
     */
    private String partyName;

    /**
     * 当事人身份证号
     */
    private String partyIdCard;

    /**
     * 当事人联系电话
     */
    private String partyPhone;

    /**
     * 当事人联系地址
     */
    private String partyAddress;

    /**
     * 经办人姓名
     */
    private String handlerName;

    /**
     * 处理情况
     */
    private String handlingDescription;

    /**
     * 事件来源
     */
    private String eventSource;

    /**
     * 事件类别
     */
    private String eventCategory;

    /**
     * 纠纷类型
     */
    private String disputeType;

    /**
     * 处理期限
     */
    private LocalDateTime handlingDeadline;

    /**
     * 事件风险等级
     */
    private String eventRiskLevel;

    /**
     * 是否办结：0否，1是
     */
    private Integer isCompleted;

    /**
     * 备注
     */
    private String remark;


    /**
     * 创建时间
     * 数据库配置了 DEFAULT CURRENT_TIMESTAMP 这里不做自动填充
     */
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     * 数据库配置了 DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP 这里不做自动填充
     */
    private LocalDateTime gmtModified;

    /**
     * 创建人用户 ID
     */
    private String createdBy;

    /**
     * 最后修改人用户 ID
     */
    private String modifiedBy;


    /**
     * 复核状态：0待复核，1已复核
     */
    private Integer reviewStatus;

    /**
     * 关键词标签展示快照
     */
    private String keywordTags;

    /**
     * 匹配状态
     */
    private Integer matchStatus;

    /**
     * 匹配人员档案 ID
     */
    private Long matchedPersonId;

    /**
     * 完成匹配时间
     */
    private LocalDateTime matchedAt;
}
