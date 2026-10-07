package com.uunnm.titletwo.business.record.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.uunnm.titletwo.business.auth.service.UserService;
import com.uunnm.titletwo.business.auth.vo.UserInfoVO;
import com.uunnm.titletwo.business.record.bo.RecordAddBO;
import com.uunnm.titletwo.business.record.bo.RecordEditBO;
import com.uunnm.titletwo.business.record.bo.RecordQueryBO;
import com.uunnm.titletwo.business.record.entity.InputRecord;
import com.uunnm.titletwo.business.record.mapper.RecordMapper;
import com.uunnm.titletwo.business.record.service.RecordService;
import com.uunnm.titletwo.business.record.service.RepeatRecordService;
import com.uunnm.titletwo.business.record.util.RecordUtil;
import com.uunnm.titletwo.business.record.vo.RecordQueryVO;
import com.uunnm.titletwo.common.entity.PageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecordServiceImpl extends ServiceImpl<RecordMapper,InputRecord> implements RecordService {
    private final UserService userService;
    private final RepeatRecordService repeatRecordService;

    @Override
    public void add(RecordAddBO recordAddBO) {
        InputRecord inputRecord = new InputRecord();
        BeanUtils.copyProperties(recordAddBO,inputRecord);
        UserInfoVO userInfo = userService.getUserInfo();

        RecordUtil recordUtil = new RecordUtil();
        String keywords = recordUtil.extraKeywords(inputRecord.getProblemDescription());

        inputRecord.setKeywordTags(keywords);

        // 创建人,最后修改人的id
        inputRecord.setCreatedBy(String.valueOf(userInfo.getId()));
        inputRecord.setModifiedBy(String.valueOf(userInfo.getId()));

        LambdaQueryWrapper<InputRecord> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper
                .eq(InputRecord::getPartyName, recordAddBO.getPartyName())
                .eq(InputRecord::getEventCategory, recordAddBO.getEventCategory())
                .eq(InputRecord::getDisputeType, recordAddBO.getDisputeType());



        save(inputRecord);
    }



    @Override
    public void del(Long id) {
        removeById(id);
    }

    @Override
    public PageVO<RecordQueryVO> page(RecordQueryBO recordQueryBO) {
        boolean isCenter = false;
        // 获取当前上下文的用户
        UserInfoVO userInfo = userService.getUserInfo();
        if (userInfo.getUnit().equals("县综治中心")) {
            isCenter = true;
        }

        Page<InputRecord> pageRequest =
                new Page<>(recordQueryBO.getCurrentPage(), recordQueryBO.getPageSize());

        Page<InputRecord> page = lambdaQuery()
                .eq(StringUtils.hasText(recordQueryBO.getPartyName()),
                        InputRecord::getPartyName,recordQueryBO.getPartyName()
                )
                .ge(recordQueryBO.getOccurredStart() != null,
                        InputRecord::getOccurredAt, recordQueryBO.getOccurredStart()
                )
                .le(recordQueryBO.getOccurredEnd() != null,
                        InputRecord::getOccurredAt, recordQueryBO.getOccurredEnd()
                )
                .ge(recordQueryBO.getGmtCreateStart() != null,
                        InputRecord::getGmtCreate, recordQueryBO.getGmtCreateStart()
                )
                .le(recordQueryBO.getGmtCreateEnd() != null,
                        InputRecord::getGmtCreate, recordQueryBO.getGmtCreateEnd()
                )
                .eq(StringUtils.hasText(recordQueryBO.getDisputeType()),
                        InputRecord::getDisputeType, recordQueryBO.getDisputeType()
                )
                .eq(!isCenter, InputRecord::getCreatedBy, userInfo.getId())
                .page(pageRequest);

        List<RecordQueryVO> voList = new ArrayList<>();
        List<InputRecord> inputRecordList = page.getRecords();
        for (InputRecord inputRecord : inputRecordList) {
            RecordQueryVO recordQueryVO = new  RecordQueryVO();
            BeanUtils.copyProperties(inputRecord,recordQueryVO);
            voList.add(recordQueryVO);
        }

        PageVO<RecordQueryVO> pageVO = new PageVO<>();
        pageVO.setTotalSize(page.getTotal());
        pageVO.setCurrentPage(recordQueryBO.getCurrentPage());
        pageVO.setPageSize(recordQueryBO.getPageSize());
        pageVO.setDataList(voList);

        return pageVO;
    }

    @Override
    public void edit(RecordEditBO editBO) {
        InputRecord inputRecord = new InputRecord();
        BeanUtils.copyProperties(editBO,inputRecord);
        updateById(inputRecord);
    }

    @Override
    public PageVO<RecordQueryVO> repeatPage(RecordQueryBO recordQueryBO) {
        
        return null;
    }

}
