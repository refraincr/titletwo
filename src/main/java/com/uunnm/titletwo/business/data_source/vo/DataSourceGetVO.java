package com.uunnm.titletwo.business.data_source.vo;

import lombok.Data;

@Data
public class DataSourceGetVO {
    /**
     * 来源名称
     */
    private String sourceName;

    /**
     * 来源类型
     */
    private String sourceType;

    /**
     * 来源状态。0停用，1启用
     */
    private Integer status;
}
