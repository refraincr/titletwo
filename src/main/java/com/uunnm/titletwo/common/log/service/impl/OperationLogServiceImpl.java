package com.uunnm.titletwo.common.log.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.uunnm.titletwo.common.entity.PageVO;
import com.uunnm.titletwo.common.log.bo.OperationLogQueryBO;
import com.uunnm.titletwo.common.log.entity.OperationLog;
import com.uunnm.titletwo.common.log.mapper.OperationLogMapper;
import com.uunnm.titletwo.common.log.service.OperationLogService;
import com.uunnm.titletwo.common.log.vo.OperationLogVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 注意：PageVO 的具体字段（records/total/pageNum/pageSize 等命名）请按你项目里
 * common.entity.PageVO 的真实定义核对，这里假设它提供 records + total 的 setter，
 * 与 MyBatis-Plus 的 Page 语义对应；如实际字段名不同，调整下方 toPageVO 方法即可，
 * 不影响本文件其余逻辑。
 */
@Service
@RequiredArgsConstructor
public class OperationLogServiceImpl implements OperationLogService {

    private final OperationLogMapper operationLogMapper;

    @Override
    public void save(OperationLog entity) {
        operationLogMapper.insert(entity);
    }

    @Override
    public PageVO<OperationLogVO> page(OperationLogQueryBO query) {
        LambdaQueryWrapper<OperationLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(query.getOperatorId() != null, OperationLog::getOperatorId, query.getOperatorId())
                .like(StringUtils.isNotBlank(query.getOperatorName()), OperationLog::getOperatorName, query.getOperatorName())
                .eq(StringUtils.isNotBlank(query.getOperationType()), OperationLog::getOperationType, query.getOperationType())
                .eq(StringUtils.isNotBlank(query.getObjectType()), OperationLog::getObjectType, query.getObjectType())
                .eq(StringUtils.isNotBlank(query.getObjectId()), OperationLog::getObjectId, query.getObjectId())
                .ge(query.getStartTime() != null, OperationLog::getOperationTime, query.getStartTime())
                .le(query.getEndTime() != null, OperationLog::getOperationTime, query.getEndTime())
                .orderByDesc(OperationLog::getOperationTime);

        Page<OperationLog> page = new Page<>(query.getCurrentPage(), query.getPageSize());
        Page<OperationLog> result = operationLogMapper.selectPage(page, wrapper);

        List<OperationLogVO> voList = result.getRecords().stream()
                .map(this::toVO)
                .collect(Collectors.toList());

        return toPageVO(voList, result.getTotal(), query);
    }

    private OperationLogVO toVO(OperationLog entity) {
        OperationLogVO vo = new OperationLogVO();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }

    private PageVO<OperationLogVO> toPageVO(List<OperationLogVO> records, long total, OperationLogQueryBO query) {
        PageVO<OperationLogVO> pageVO = new PageVO<>();
        pageVO.setDataList(records);
        pageVO.setTotalSize(total);
        pageVO.setCurrentPage(query.getCurrentPage());
        pageVO.setPageSize(query.getPageSize());
        return pageVO;
    }
}