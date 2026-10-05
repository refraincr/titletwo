package com.uunnm.titletwo.business.auth.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Units {
    EDUCATION_SPORTS_BUREAU("教体局"),
    HEALTH_COMMISSION("卫健委"),
    HOUSING_CONSTRUCTION_BUREAU("住建局"),
    MARKET_REGULATION_BUREAU("市场监管局"),
    URBAN_MANAGEMENT_BUREAU("城管局"),
    STATE_OWNED_GROUP("国控集团"),
    CIVIL_AFFAIRS_BUREAU("民政局"),
    HUMAN_RESOURCES_SOCIAL_SECURITY_BUREAU("人社局"),
    TRANSPORTATION_BUREAU("交通运输"),
    WATER_RESOURCES_BUREAU("水利局"),
    ECOLOGY_ENVIRONMENT_BUREAU("环保局"),
    AGRICULTURE_RURAL_BUREAU("农村农业局"),
    WOMEN_FEDERATION("妇联"),
    TRADE_UNION_FEDERATION("总工会"),
    NATURAL_RESOURCES_PLANNING_BUREAU("自然资源和规划"),
    COUNTY_PETITION_BUREAU("县信访局"),

    E_HU_TOWN("鹅湖镇"),
    FU_LIANG_TOWN("浮梁镇"),
    HUANG_TAN_TOWNSHIP("黄坛乡"),
    JIANG_CUN_TOWNSHIP("江村乡"),
    JING_GONG_QIAO_TOWN("经公桥镇"),
    JIAO_TAN_TOWN("蛟潭镇"),
    LE_GONG_TOWNSHIP("勒功乡"),
    SHOU_AN_TOWN("寿安镇"),
    SAN_LONG_TOWN("三龙镇"),
    WANG_GANG_TOWNSHIP("王港乡"),
    XIANG_HU_TOWN("湘湖镇"),
    XI_HU_TOWNSHIP("西湖乡"),
    XING_TIAN_TOWNSHIP("兴田乡"),
    YAO_LI_TOWN("瑶里镇"),
    ZHI_TAN_TOWN("峙滩镇"),
    ZANG_WAN_TOWNSHIP("臧湾乡"),
    COMMUNITY_MANAGEMENT_COMMITTEE("社区管委会"),

    COURT("法院"),
    PROCURATORATE("检察院"),
    POLICE_CYBER_SECURITY("公安局网安大队"),
    POLICE_COMMAND_CENTER("公安局情指中心"),
    POLICE_TRAFFIC_POLICE("公安局交管大队"),
    POLICE_FOREST_POLICE("公安局森警大队"),
    JUSTICE_BUREAU("司法局");


    private final String name;
}
