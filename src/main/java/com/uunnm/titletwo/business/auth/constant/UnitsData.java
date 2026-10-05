package com.uunnm.titletwo.business.auth.constant;

import com.uunnm.titletwo.business.auth.vo.UnitsVO;

import java.util.List;


public class UnitsData {

    public static final List<UnitsVO> UNITS = List.of(
            new UnitsVO(
                    "风险分析研判单位",
                    "ANALYST",
                    List.of(
                            "县综治中心"
                    )
            ),

            new UnitsVO(
                    "政法及司法单位",
                    "REPORTER",
                    List.of(
                            "法院",
                            "检察院",
                            "公安局网安大队",
                            "公安局情指中心",
                            "公安局交管大队",
                            "公安局森警大队",
                            "司法局"
                    )
            ),

            new UnitsVO(
                    "县直职能部门及相关单位",
                    "REPORTER",
                    List.of(
                            "教体局",
                            "卫健委",
                            "住建局",
                            "市场监管局",
                            "城管局",
                            "国控集团",
                            "民政局",
                            "人社局",
                            "交通运输",
                            "水利局",
                            "环保局",
                            "农村农业局",
                            "妇联",
                            "总工会",
                            "自然资源和规划",
                            "县信访局"
                    )
            ),

            new UnitsVO(
                    "乡镇及基层治理单位",
                    "REPORTER",
                    List.of(
                            "鹅湖镇",
                            "浮梁镇",
                            "黄坛乡",
                            "江村乡",
                            "经公桥镇",
                            "蛟潭镇",
                            "勒功乡",
                            "寿安镇",
                            "三龙镇",
                            "王港乡",
                            "湘湖镇",
                            "西湖乡",
                            "兴田乡",
                            "瑶里镇",
                            "峙滩镇",
                            "臧湾乡",
                            "社区管委会"
                    )
            ),

            new UnitsVO(
                    "特殊角色账号",
                    "KEY_PERSON",
                    List.of(
                            "重点人群"
                    )
            )
    );

    private UnitsData() {
    }
}
