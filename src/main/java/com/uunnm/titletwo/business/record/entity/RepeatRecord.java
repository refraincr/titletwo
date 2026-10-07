package com.uunnm.titletwo.business.record.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("t_repeat_record")
@Data
public class RepeatRecord {
    @TableId(value = "id", type= IdType.ASSIGN_ID)
    private Long id;

    private String department;

    private Long record_id;
}
