package com.uunnm.titletwo.business.record.util;


import com.uunnm.titletwo.business.record.contant.KeywordTags;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
}
