package com.uunnm.titletwo.business.person_profile.util;

import com.uunnm.titletwo.business.person_profile.entity.PersonProfile;
import com.uunnm.titletwo.business.record.entity.InputRecord;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParamConvertor {
    public PersonProfile recordAddBOToPersonProfile(InputRecord inputRecord) {
        PersonProfile personProfile = new PersonProfile();
        personProfile.setName(inputRecord.getPartyName());
        personProfile.setPhone(inputRecord.getPartyPhone());
        if (inputRecord.getPartyIdCard() == null || !inputRecord.getPartyIdCard().isEmpty()) {
            personProfile.setIdCard(inputRecord.getPartyIdCard());
        }
        personProfile.setLastOccurredAt(inputRecord.getOccurredAt());

        // 统一 keywords 的类型
        ObjectMapper mapper = new ObjectMapper();
        List<String> keywordsList = mapper.readValue(inputRecord.getKeywordTags(), new TypeReference<>(){});
        Map<String,Integer> keywordsMap = new HashMap<>();
        keywordsList.forEach(keyword -> keywordsMap.put(keyword,1));

        personProfile.setKeywords(mapper.writeValueAsString(keywordsMap));

        return personProfile;
    }
}
