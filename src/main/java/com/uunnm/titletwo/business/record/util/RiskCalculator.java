package com.uunnm.titletwo.business.record.util;

import com.uunnm.titletwo.business.auth.vo.UserInfoVO;
import com.uunnm.titletwo.business.person_profile.entity.PersonProfile;

import java.util.List;

public class RiskCalculator {
    public String getRisk(PersonProfile personProfile, UserInfoVO userInfo, String keywords) {
        String riskLevel = personProfile.getEventRiskLevel();

        if (riskLevel == null){
            riskLevel =  "LOW";
        }

        // 是否由重点人群录入
        if (userInfo.getRole().equals("KEY_PERSON")) {
            if (riskLevel.equals("LOW")) {
                riskLevel = "NORMAL";
            }
        }

        // 关键词，关键词组合
        for (String s : List.of("扬言", "杀人", "跳楼")) {
            if (keywords.contains(s)) {
                if (riskLevel.equals("LOW")) {
                    riskLevel = "NORMAL";
                }
            }
        }

        if (keywords.contains("婚") && keywords.contains("欠")) {
            if (riskLevel.equals("LOW")) {
                riskLevel = "NORMAL";
            }
        }

        if (keywords.contains("婚") && keywords.contains("钱")) {
            if (riskLevel.equals("LOW")) {
                riskLevel = "NORMAL";
            }
        }
        return riskLevel;
    }
}
