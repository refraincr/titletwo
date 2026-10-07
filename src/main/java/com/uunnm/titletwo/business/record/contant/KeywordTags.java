package com.uunnm.titletwo.business.record.contant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum KeywordTags {
    // ========== 危险 / 极端行为 ==========
    THREATEN("扬言"),
    KILL("杀人"),
    JUMP_OFF_A_BUILDING("跳楼"),
    SELF_HARM("自残"),
    SUICIDE("自杀"),
    POISON("投毒"),
    ARSON("纵火"),
    BOMB("爆炸"),
    KNIFE("持刀"),
    HIJACK("劫持"),
    KIDNAP("绑架"),
    VIOLENCE("暴力"),
    ABUSE("虐待"),
    THREAT_LETTER("恐吓"),
    REVENGE("报复"),

    // ========== 经济 / 债务 ==========
    WEDDING("婚"),
    MONEY("钱"),
    OWE("欠"),
    LOAN("贷款"),
    DEBT("债务"),
    FRAUD("诈骗"),
    GAMBLE("赌博"),
    BRIBE("贿赂"),
    TAX_EVASION("偷税"),
    MONEY_LAUNDERING("洗钱"),
    BANKRUPTCY("破产"),
    SALARY("工资"),
    COMPENSATION("赔偿"),

    // ========== 家庭 / 关系 ==========
//    MARRIAGE("婚姻"),
//    DIVORCE("离婚"),
    LOVER("情人"),
    BREAKUP("分手"),
    DOMESTIC_VIOLENCE("家暴"),

    // ========== 工作 / 职场 ==========
    WORKPLACE_BULLYING("职场霸凌"),

    // ========== 法律 / 纠纷 ==========
    LAWSUIT("起诉"),
    ARREST("逮捕"),
    PRISON("坐牢"),
    DISPUTE("纠纷"),
    RIGHTS("维权"),

    // ========== 网络 / 舆情 ==========
    RUMOR("谣言"),
    CYBERBULLYING("网暴"),
    LEAK("泄露"),
    HACK("黑客"),
    VIRUS("病毒");


    private final String tagName;
}