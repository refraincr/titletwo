package com.uunnm.titletwo.business.record.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.uunnm.titletwo.business.auth.service.UserService;
import com.uunnm.titletwo.business.auth.vo.UserInfoVO;
import com.uunnm.titletwo.business.person_profile.entity.PersonProfile;
import com.uunnm.titletwo.business.person_profile.service.PersonProfileService;
import com.uunnm.titletwo.business.record.bo.RecordAddBO;
import com.uunnm.titletwo.business.record.bo.RecordEditBO;
import com.uunnm.titletwo.business.record.bo.RecordQueryBO;
import com.uunnm.titletwo.business.record.entity.InputRecord;
import com.uunnm.titletwo.business.record.mapper.RecordMapper;
import com.uunnm.titletwo.business.record.service.RecordService;
import com.uunnm.titletwo.business.record.util.RecordUtil;
import com.uunnm.titletwo.business.record.util.RiskCalculator;
import com.uunnm.titletwo.business.record.vo.KeywordStatVO;
import com.uunnm.titletwo.business.record.vo.RecordQueryVO;
import com.uunnm.titletwo.common.entity.PageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecordServiceImpl extends ServiceImpl<RecordMapper,InputRecord> implements RecordService {
    private final UserService userService;
    private final PersonProfileService personProfileService;

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

        // 人员档案更新
        personProfileService.processNewEvent(
                recordAddBO,
                keywords,
                getCountByNameAndPhone(recordAddBO.getPartyName(),recordAddBO.getPartyPhone())
        );

        //基础风险等级
        PersonProfile personProfile = personProfileService.getByNameAndPhone(
                recordAddBO.getPartyName(),
                recordAddBO.getPartyPhone()
        );
        RiskCalculator riskCalculator =  new RiskCalculator();
        String riskLevel = riskCalculator.getRisk(personProfile, userInfo, keywords);

        inputRecord.setEventRiskLevel(riskLevel);

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
    public Integer getCountByNameAndPhone(String name, String phone) {
        LambdaQueryWrapper<InputRecord> wrapper = Wrappers.lambdaQuery();
        wrapper
                .eq(InputRecord::getPartyName, name)
                .eq(InputRecord::getPartyPhone, phone);
        return (int) count(wrapper);
    }

    /**
     * 获取全部的关键词
     * @return {k1:count,k2:count...}
     */
    @Override
    public List<KeywordStatVO> keywords() {
        return baseMapper.selectKeywordStats();
    }

    /**
     * 根据关键词获取相关的事件
     * @param keyword 关键词
     * @return 相关事件的分页结果
     */
    @Override
    public List<RecordQueryVO> queryByKeywords(String keyword) {
        LambdaQueryWrapper<InputRecord> wrapper = Wrappers.lambdaQuery();
        wrapper.like(InputRecord::getKeywordTags, keyword);
        List<InputRecord> list = list(wrapper);
        List<RecordQueryVO> voList = new ArrayList<>();
        list.forEach(inputRecord -> {
            RecordQueryVO recordQueryVO = new  RecordQueryVO();
            BeanUtils.copyProperties(inputRecord,recordQueryVO);
            voList.add(recordQueryVO);
        });
        return voList;
    }
}
