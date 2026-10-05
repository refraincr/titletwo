package com.uunnm.titletwo.business.record.bo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 纠纷事件记录新增参数
 */
@Data
public class RecordAddBO {


    /**
     * 当事人姓名
     */
    private String partyName;


    /**
     * 当事人联系电话
     */
    private String partyPhone;


    /**
     * 当事人联系地址
     */
    private String partyAddress;


    /**
     * 当事人身份证号
     */
    private String partyIdCard;


    /**
     * 事件发生时间
     */
    @NotNull(message = "发生时间不能为空")
    private LocalDateTime occurredAt;


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
     * <p>
     * 示例：
     * 群众上报、12345热线、网格巡查
     */
    private String eventSource;


    /**
     * 事件类别
     * <p>
     * 示例：
     * 民事纠纷、劳动纠纷、邻里纠纷
     */
    private String eventCategory;


    /**
     * 纠纷类型
     */
    @NotBlank(message = "纠纷类型不能为空")
    private String disputeType;


    /**
     * 风险等级
     * <p>
     * 示例：
     * LOW、NORMAL、HIGH
     */
    private String eventRiskLevel;


    /**
     * 处理期限
     */
    private LocalDateTime handlingDeadline;


    /**
     * 是否办结
     * <p>
     * 0：否
     * 1：是
     */
    private Integer isCompleted;


    /**
     * 具体问题描述
     */
    @NotBlank(message = "具体问题不能为空")
    private String problemDescription;


    /**
     * 备注
     */
    private String remark;

}