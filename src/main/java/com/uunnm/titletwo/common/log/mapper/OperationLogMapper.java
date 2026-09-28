package com.uunnm.titletwo.common.log.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.uunnm.titletwo.common.log.entity.OperationLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OperationLogMapper extends BaseMapper<OperationLog> {
}
