package com.uunnm.titletwo.common.log.annotation;

import java.lang.annotation.*;

/**
 * 标注在 Service 方法上，自动记录操作人/时间/类型/对象/前后摘要。*
 * 用法示例：
 * <pre>
 * {@literal @}OperationLog(
 *     type = "等级调整",
 *     objectType = "person_archive",
 *     objectIdExpr = "#dto.archiveId",
 *     beforeExpr = "#dto.oldLevel",
 *     afterExpr = "#dto.newLevel")
 * public void adjustRiskLevel(LevelAdjustDTO dto) { ... }
 * </pre>
 *
 * 约定：objectIdExpr / beforeExpr / afterExpr 均为 SpEL 表达式，只能从方法入参
 * （#参数名）中取值，不会反查数据库。没有可取的旧值时 beforeExpr 可留空。*
 * 注意：本注解只在有登录上下文（SecurityContextHolder 中能取到当前用户）的
 * 方法上生效；无登录上下文的场景（如模拟接口 API Key 鉴权路径）不适用，
 * 该类场景如需记录操作日志，由业务代码手动调用 OperationLogService 写入。
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperationLog {

    /** 操作类型，中文，如"规则变更"/"人工审核"/"等级调整"/"流程处置"/"数据导入" */
    String type();

    /** 操作对象类型，英文表名/实体标识，如 person_archive */
    String objectType() default "";

    /** SpEL 表达式，从方法入参中取操作对象ID，如 "#dto.archiveId" */
    String objectIdExpr() default "";

    /** SpEL 表达式，从方法入参中取操作前摘要，如 "#dto.oldLevel"，留空表示无 */
    String beforeExpr() default "";

    /** SpEL 表达式，从方法入参中取操作后摘要，如 "#dto.newLevel" */
    String afterExpr() default "";
}