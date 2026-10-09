package com.uunnm.titletwo.business.person_profile.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.uunnm.titletwo.business.person_profile.entity.PersonProfile;
import com.uunnm.titletwo.business.person_profile.mapper.PersonProfileMapper;
import com.uunnm.titletwo.business.person_profile.service.PersonProfileService;
import com.uunnm.titletwo.business.person_profile.util.ParamConvertor;
import com.uunnm.titletwo.business.record.bo.RecordAddBO;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import tools.jackson.core.type.TypeReference;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class PersonProfileServiceImpl extends ServiceImpl<PersonProfileMapper, PersonProfile> implements PersonProfileService {

    @Override
    public void processNewEvent(RecordAddBO recordAddBO, String keywords,int eventCount) {
        ParamConvertor convertor = new ParamConvertor();
        PersonProfile personProfile = convertor.recordAddBOToPersonProfile(recordAddBO,keywords);
        PersonProfile oldPersonprofile = getByNameAndPhone(personProfile.getName(), personProfile.getPhone());

        // 计算基础风险等级
        if (eventCount>5){
            personProfile.setEventRiskLevel("HIGHT");
        } else if  (eventCount>2){
            personProfile.setEventRiskLevel("NORMAL");
        } else if  (eventCount>=0){
            personProfile.setEventRiskLevel("LOW");
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
        }

        save(personProfile);
    }


    @Override
    public PersonProfile getByNameAndPhone(String name, String phone) {
        LambdaQueryWrapper<PersonProfile> wrapper = Wrappers.lambdaQuery();
        wrapper
                .eq(PersonProfile::getName,name)
                .eq(PersonProfile::getPhone,phone);
        return getOne(wrapper);
    }


}
