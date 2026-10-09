package com.uunnm.titletwo.business.person_profile.util;

import com.uunnm.titletwo.business.person_profile.entity.PersonProfile;
import com.uunnm.titletwo.business.record.bo.RecordAddBO;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParamConvertor {
    public PersonProfile recordAddBOToPersonProfile(RecordAddBO recordAddBO, String keywords) {
        PersonProfile personProfile = new PersonProfile();
        personProfile.setName(recordAddBO.getPartyName());
        personProfile.setPhone(recordAddBO.getPartyPhone());
        personProfile.setIdCard(recordAddBO.getPartyIdCard());
        personProfile.setLastOccurredAt(recordAddBO.getOccurredAt());

        // 统一 keywords 的类型
        ObjectMapper mapper = new ObjectMapper();
        List<String> keywordsList = mapper.readValue(keywords, new TypeReference<>(){});
        Map<String,Integer> keywordsMap = new HashMap<>();
        keywordsList.forEach(keyword -> keywordsMap.put(keyword,1));

        personProfile.setKeywords(mapper.writeValueAsString(keywordsMap));

        return personProfile;
    }
}
