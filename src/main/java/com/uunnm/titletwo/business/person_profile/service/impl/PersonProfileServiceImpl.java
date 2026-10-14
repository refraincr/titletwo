package com.uunnm.titletwo.business.person_profile.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.uunnm.titletwo.business.person_profile.bo.ArchivePageBO;
import com.uunnm.titletwo.business.person_profile.entity.PersonProfile;
import com.uunnm.titletwo.business.person_profile.mapper.PersonProfileMapper;
import com.uunnm.titletwo.business.person_profile.service.PersonProfileService;
import com.uunnm.titletwo.business.person_profile.util.ParamConvertor;
import com.uunnm.titletwo.business.person_profile.vo.ArchiveVO;
import com.uunnm.titletwo.business.record.bo.RecordAddBO;
import com.uunnm.titletwo.common.entity.PageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tools.jackson.databind.ObjectMapper;

import tools.jackson.core.type.TypeReference;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class PersonProfileServiceImpl extends ServiceImpl<PersonProfileMapper, PersonProfile> implements PersonProfileService {

    @Override
    public PersonProfile processNewEvent(RecordAddBO recordAddBO, String keywords,int eventCount) {
        ParamConvertor convertor = new ParamConvertor();
        PersonProfile personProfile = convertor.recordAddBOToPersonProfile(recordAddBO,keywords);
        PersonProfile oldPersonprofile = getByNameAndPhone(personProfile.getName(), personProfile.getPhone());

        // 计算基础风险等级（这个是人员的风险等级，注意不要和事件风险等级混淆）
        if (eventCount>5){
            personProfile.setRiskLevel("HIGHT");
        } else if  (eventCount>2){
            personProfile.setRiskLevel("NORMAL");
        } else if  (eventCount>=0){
            personProfile.setRiskLevel("LOW");
        }


        if(oldPersonprofile!=null){
            // 关键词
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String,Integer> keywordsMap = objectMapper.readValue(
                    oldPersonprofile.getKeywords(),
                    new TypeReference<>() {}
            );
            List<String> keywordsList = objectMapper.readValue(
                    keywords,
                    new TypeReference<>() {}
            );
            keywordsList.forEach(keyword->{
                if (keywordsMap.containsKey(keyword)){
                    keywordsMap.put(keyword,keywordsMap.get(keyword)+1);
                } else {
                    keywordsMap.put(keyword, 1);
                }
            });
            personProfile.setKeywords(objectMapper.writeValueAsString(keywordsMap));

            // 最近发生时间
            LocalDateTime personProfileTime = oldPersonprofile.getLastOccurredAt();
            LocalDateTime currentEventTime = personProfile.getLastOccurredAt();
            if (personProfileTime.isAfter(currentEventTime)){
                personProfile.setLastOccurredAt(personProfileTime);
            }

            // 更新事件需要 id
            personProfile.setId(oldPersonprofile.getId());
            updateById(personProfile);
        } else {
            save(personProfile);
        }

        return getByNameAndPhone(recordAddBO.getPartyName(), recordAddBO.getPartyPhone());
    }


    @Override
    public PersonProfile getByNameAndPhone(String name, String phone) {
        LambdaQueryWrapper<PersonProfile> wrapper = Wrappers.lambdaQuery();
        wrapper
                .eq(PersonProfile::getName,name)
                .eq(PersonProfile::getPhone,phone);
        return getOne(wrapper);
    }

    /**
     * 分页查询所有的人员档案集
     * @param archivePageBO 入参(可根据姓名，电话，身份证查询)
     * @return 返回分页查询结果
     */
    @Override
    public PageVO<ArchiveVO> page(ArchivePageBO archivePageBO) {
        Page<PersonProfile> pageRequest = new Page<>(
                archivePageBO.getCurrentPage(),
                archivePageBO.getPageSize()
        );
        Page<PersonProfile> page = lambdaQuery()
                .eq(StringUtils.hasText(archivePageBO.getName()), PersonProfile::getName, archivePageBO.getName())
                .eq(StringUtils.hasText(archivePageBO.getPhone()), PersonProfile::getPhone, archivePageBO.getPhone())
                .eq(StringUtils.hasText(archivePageBO.getIdCard()), PersonProfile::getIdCard, archivePageBO.getIdCard())
                .page(pageRequest);

        List<ArchiveVO> list = page.getRecords()
                .stream()
                .map(person -> {
                    ArchiveVO archiveVO = new ArchiveVO();
                    BeanUtils.copyProperties(person, archiveVO);
                    return archiveVO;
                })
                .toList();

        PageVO<ArchiveVO> pageVO = new PageVO<>();
        pageVO.setCurrentPage(page.getCurrent());
        pageVO.setPageSize(page.getSize());
        pageVO.setTotalSize(page.getTotal());
        pageVO.setDataList(list);

        return pageVO;
    }

    @Override
    public List<PersonProfile> processNewEvents() {
        return List.of();
    }
}
