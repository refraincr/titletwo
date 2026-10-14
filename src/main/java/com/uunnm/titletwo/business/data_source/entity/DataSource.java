package com.uunnm.titletwo.business.data_source.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_data_source")
public class DataSource {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

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

    /**
     * 事件id
     */
    private Long recordId;
}
