package com.uunnm.titletwo.business.record.util;

import com.uunnm.titletwo.business.auth.vo.UserInfoVO;
import com.uunnm.titletwo.business.person_profile.entity.PersonProfile;
import com.uunnm.titletwo.business.risk.bo.RiskAddBO;
import com.uunnm.titletwo.business.risk.service.RiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

@Component
public class RiskCalculator {
    private static final String low = "LOW";
    private static final String normal = "NORMAL";
//    private static final String high = "HIGH";

    @Autowired
    private RiskService riskService;

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

    public String getRisk(PersonProfile personProfile, UserInfoVO userInfo, String keywords, Long recordId, int eventCount) {
        String riskLevel = personProfile.getRiskLevel();

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
                break;
            }
        }

        if (keywords.contains("婚") && keywords.contains("欠")) {
            riskLevel = ensureAtLeastMediumRiskLevel(riskLevel);
        }

        if (keywords.contains("婚") && keywords.contains("钱")) {
            riskLevel = ensureAtLeastMediumRiskLevel(riskLevel);
        }

        saveCalculationBasis(userInfo, keywords, recordId, eventCount);

        return riskLevel;
    }

    private void saveCalculationBasis(UserInfoVO userInfo,
                                      String keywords,
                                      Long recordId,
                                      int eventCount
    ) {
        RiskAddBO riskAddBO = new RiskAddBO();
        riskAddBO.setPartyEventCount(eventCount);
        riskAddBO.setRecordId(recordId);
        riskAddBO.setIsSpecialAccount(false);

        // 模拟计算风险等级

        // 是否由重点人群录入
        if (userInfo.getRole().equals("KEY_PERSON")) {
            riskAddBO.setIsSpecialAccount(true);
        }

        List<String> keywordsList = new ArrayList<>();

        // 关键词，关键词组合
        for (String s : List.of("扬言", "杀人", "跳楼")) {
            if (keywords.contains(s)) {
                keywordsList.add(s);
                break;
            }
        }

        if (keywords.contains("婚") && keywords.contains("欠")) {
            keywordsList.add("婚+欠");
        }

        if (keywords.contains("婚") && keywords.contains("钱")) {
            keywordsList.add("婚+钱");
        }

        ObjectMapper mapper = new ObjectMapper();
        riskAddBO.setKeywords(mapper.writeValueAsString(keywordsList));

        // 保存到数据库
        riskService.input(riskAddBO);
    }
}
