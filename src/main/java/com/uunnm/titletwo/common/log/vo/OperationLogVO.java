package com.uunnm.titletwo.common.log.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OperationLogVO {

    private Long id;

    private Long operatorId;

    private String operatorName;

    private String operationType;

    private String objectType;

    private String objectId;

    private String beforeSummary;

    private String afterSummary;

    private LocalDateTime operationTime;
}