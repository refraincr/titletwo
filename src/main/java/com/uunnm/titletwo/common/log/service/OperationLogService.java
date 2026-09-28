package com.uunnm.titletwo.common.log.service;

import com.uunnm.titletwo.common.entity.PageVO;
import com.uunnm.titletwo.common.log.bo.OperationLogQueryBO;
import com.uunnm.titletwo.common.log.entity.OperationLog;
import com.uunnm.titletwo.common.log.vo.OperationLogVO;

public interface OperationLogService {

    /** 写入一条操作日志。供 AOP 切面调用，也供无登录上下文场景（如模拟接口）手动调用 */
    void save(OperationLog entity);

    /** 分页查询，供后续功能票复用（如档案详情页"查看操作历史"、规则管理页"变更记录"） */
    PageVO<OperationLogVO> page(OperationLogQueryBO query);
}