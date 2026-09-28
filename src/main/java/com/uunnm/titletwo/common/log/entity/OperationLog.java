package com.uunnm.titletwo.common.log.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 操作日志实体。
 * 注意：不继承 common.entity.CommonEntity —— 日志记录是一次性写入、不会更新，
 * CommonEntity 若含 updateTime/isDeleted 等字段对本表无意义，故单独定义。
 */
@Data
@TableName("operation_log")
public class OperationLog {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /** 操作人ID，取自 SecurityContext 中的当前登录用户 */
    private Long operatorId;

    /** 操作人姓名/用户名冗余存储，避免列表查询再关联 sys_user */
    private String operatorName;

    /** 操作类型，中文字符串，如"等级调整"。不建枚举，便于后续功能票直接扩展新类型 */
    private String operationType;

    /** 操作对象类型，英文表名/实体标识，如 person_archive，用于精确匹配过滤 */
    private String objectType;

    /** 操作对象ID，存字符串以兼容非数字主键场景 */
    private String objectId;

    /** 操作前内容摘要，来自 @OperationLog 注解 beforeExpr 的 SpEL 求值结果 */
    private String beforeSummary;

    /** 操作后内容摘要，来自 @OperationLog 注解 afterExpr 的 SpEL 求值结果 */
    private String afterSummary;

    private LocalDateTime operationTime;
}