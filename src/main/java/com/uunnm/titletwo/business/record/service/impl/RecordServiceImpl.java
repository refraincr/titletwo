package com.uunnm.titletwo.business.record.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.uunnm.titletwo.business.auth.service.UserService;
import com.uunnm.titletwo.business.auth.vo.UserInfoVO;
import com.uunnm.titletwo.business.person_profile.entity.PersonProfile;
import com.uunnm.titletwo.business.person_profile.service.PersonProfileService;
import com.uunnm.titletwo.business.record.bo.PersonQueryBO;
import com.uunnm.titletwo.business.record.bo.RecordAddBO;
import com.uunnm.titletwo.business.record.bo.RecordEditBO;
import com.uunnm.titletwo.business.record.bo.RecordQueryBO;
import com.uunnm.titletwo.business.record.entity.InputRecord;
import com.uunnm.titletwo.business.record.mapper.RecordMapper;
import com.uunnm.titletwo.business.record.service.RecordService;
import com.uunnm.titletwo.business.record.util.FileUtil;
import com.uunnm.titletwo.business.record.util.RecordUtil;
import com.uunnm.titletwo.business.record.util.RiskCalculator;
import com.uunnm.titletwo.business.record.vo.KeywordStatVO;
import com.uunnm.titletwo.business.record.vo.PersonEventCountVO;
import com.uunnm.titletwo.business.record.vo.RecordQueryVO;
import com.uunnm.titletwo.common.entity.PageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class RecordServiceImpl extends ServiceImpl<RecordMapper,InputRecord> implements RecordService {
    private final UserService userService;
    private final PersonProfileService personProfileService;

    private final RecordUtil recordUtil;
    private final RiskCalculator riskCalculator;
    private final FileUtil fileUtil;

    /**
     * 先保存不完整的记录再自动更新
     * @param recordAddBO 添加记录的入参
     */
    @Override
    public void input(RecordAddBO recordAddBO) {
        UserInfoVO userInfo = userService.getUserInfo();
        InputRecord inputRecord = recordUtil.extraInputRecord(recordAddBO, userInfo);
        save(inputRecord);

        // 人员档案更新
        int eventCount = getCountByNameAndPhone(recordAddBO.getPartyName(),recordAddBO.getPartyPhone());
        PersonProfile personProfile = personProfileService.processNewEvent(inputRecord,eventCount);

        // 更新相关事件的风险等级
        updatePersonRelatedEvents(personProfile);
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
                .like(StringUtils.hasText(recordQueryBO.getKeyword()),
                        InputRecord::getKeywordTags, recordQueryBO.getKeyword())
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
        UserInfoVO userInfo = userService.getUserInfo();
        inputRecord.setModifiedBy(String.valueOf(userInfo.getId()));
        updateById(inputRecord);
    }

    public Integer getCountByNameAndPhone(String name, String phone) {
        LambdaQueryWrapper<InputRecord> wrapper = Wrappers.lambdaQuery();
        wrapper
                .eq(InputRecord::getPartyName, name)
                .eq(InputRecord::getPartyPhone, phone);
        return (int) count(wrapper);
    }

    public List<InputRecord> queryByNameAndPhone(String name, String phone) {
        LambdaQueryWrapper<InputRecord> wrapper = Wrappers.lambdaQuery();
        wrapper
                .eq(InputRecord::getPartyName, name)
                .eq(InputRecord::getPartyPhone, phone);
        return list(wrapper);
    }

    /**
     * 自动计算人员相关事件的风险等级
     * @param personProfile 人员档案
     */
    private void updatePersonRelatedEvents(PersonProfile personProfile) {
        UserInfoVO userInfo = userService.getUserInfo();
        List<InputRecord> inputRecords = queryByNameAndPhone(personProfile.getName(), personProfile.getPhone());
        inputRecords.forEach(inputRecord -> {
            String riskLevel = riskCalculator.getRisk(
                    personProfile,
                    userInfo,
                    inputRecord.getKeywordTags(),
                    inputRecord.getId(),
                    inputRecords.size()
            );
            inputRecord.setEventRiskLevel(riskLevel);
            LambdaUpdateWrapper<InputRecord> wrapper = Wrappers.lambdaUpdate(InputRecord.class)
                    .eq(InputRecord::getPartyName, inputRecord.getPartyName())
                    .eq(InputRecord::getPartyPhone, inputRecord.getPartyPhone())
                    .eq(InputRecord::getKeywordTags, inputRecord.getKeywordTags());
            update(inputRecord, wrapper);
        });
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

    /**
     * 处理 csv, excel 文件数据导入
     * @param file 传来的文件
     */
    @Override
    public void fileInput(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("上传文件不能为空");
        }

        String fileName = file.getOriginalFilename();

        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("上传文件不能为空");
        }

        String suffix = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();

        List<InputRecord> inputRecords = new ArrayList<>();
        switch (suffix) {
            case ".xls", ".xlsx" -> fileUtil.processExcel(file, inputRecords);
            case ".csv" -> fileUtil.processCsv(file, inputRecords);
        }

        // 提取并设置问题描述的关键词
        inputRecords.forEach(inputRecord -> {
            String keywords = recordUtil.extraKeywords(inputRecord.getProblemDescription());
            inputRecord.setKeywordTags(keywords);
        });

        saveBatch(inputRecords);

        // 查询有关人员的关联事件次数
        List<PersonQueryBO> personQueryBOS = inputRecords.stream()
                .map(inputRecord ->
                        new PersonQueryBO(inputRecord.getPartyName(), inputRecord.getPartyPhone()))
                .distinct()
                .toList();

        List<PersonEventCountVO> personEventCountVOS = baseMapper.selectPersonEventCount(personQueryBOS);

        List<PersonProfile> personProfiles = personProfileService.processNewEvents(inputRecords, personEventCountVOS);
        personProfiles.forEach(this::updatePersonRelatedEvents);
    }
}
