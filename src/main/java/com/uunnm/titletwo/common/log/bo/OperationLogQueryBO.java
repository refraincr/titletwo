package com.uunnm.titletwo.common.log.bo;

import com.uunnm.titletwo.common.entity.PageBO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 注意：假设 common.entity.PageBO 已携带分页参数（如 pageNum/pageSize），
 * 若实际字段名不同，请按你项目里 PageBO 的真实定义调整继承关系或字段名。
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OperationLogQueryBO extends PageBO {

    private Long operatorId;

    private String operatorName;

    private String operationType;

    private String objectType;

    private String objectId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;
}