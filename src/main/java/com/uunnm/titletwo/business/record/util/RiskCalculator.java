package com.uunnm.titletwo.business.record.util;

import com.uunnm.titletwo.business.auth.vo.UserInfoVO;
import com.uunnm.titletwo.business.person_profile.entity.PersonProfile;

import java.util.List;

public class RiskCalculator {
    private static final String low = "LOW";
    private static final String normal = "NORMAL";
//    private static final String high = "HIGH";

    /**
     * 确保风险等级至少提升至中级
     * @param riskLevel 原风险等级
     * @return 提升后的风险等级
     */
    private String ensureAtLeastMediumRiskLevel(String riskLevel) {
        if (riskLevel.equals(low)) {
            return normal;
        }
        return riskLevel;
    }

    public String getRisk(PersonProfile personProfile, UserInfoVO userInfo, String keywords) {
        String riskLevel = personProfile.getEventRiskLevel();

        if (riskLevel == null){
            riskLevel = "LOW";
        }

        // 是否由重点人群录入
        if (userInfo.getRole().equals("KEY_PERSON")) {
            riskLevel = ensureAtLeastMediumRiskLevel(riskLevel);
        }

        // 关键词，关键词组合
        for (String s : List.of("扬言", "杀人", "跳楼")) {
            if (keywords.contains(s)) {
                riskLevel = ensureAtLeastMediumRiskLevel(riskLevel);
            }
        }

        if (keywords.contains("婚") && keywords.contains("欠")) {
            riskLevel = ensureAtLeastMediumRiskLevel(riskLevel);
        }

        if (keywords.contains("婚") && keywords.contains("钱")) {
            riskLevel = ensureAtLeastMediumRiskLevel(riskLevel);
        }
        return riskLevel;
    }
}
