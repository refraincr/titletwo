package com.uunnm.titletwo.business.record.util;


import com.uunnm.titletwo.business.auth.vo.UserInfoVO;
import com.uunnm.titletwo.business.record.bo.RecordAddBO;
import com.uunnm.titletwo.business.record.contant.KeywordTags;
import com.uunnm.titletwo.business.record.entity.InputRecord;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class RecordUtil {
    public String extraKeywords(String description) {
        List<String> keywords = new ArrayList<>();
        Arrays.stream(KeywordTags.values()).forEach(kws -> {
            if (description.contains(kws.getTagName())) {
                keywords.add(kws.getTagName());
            }
        });
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(keywords);
    }

    public InputRecord extraInputRecord(RecordAddBO recordAddBO, UserInfoVO userInfo) {
        InputRecord inputRecord = new InputRecord();
        BeanUtils.copyProperties(recordAddBO,inputRecord);

        String keywords = extraKeywords(inputRecord.getProblemDescription());

        inputRecord.setKeywordTags(keywords);

        // 创建人, 最后修改人的id
        inputRecord.setCreatedBy(String.valueOf(userInfo.getId()));
        inputRecord.setModifiedBy(String.valueOf(userInfo.getId()));

        return inputRecord;
    }
}
