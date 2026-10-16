package com.uunnm.titletwo.business.record.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.uunnm.titletwo.business.record.bo.PersonQueryBO;
import com.uunnm.titletwo.business.record.entity.InputRecord;
import com.uunnm.titletwo.business.record.vo.KeywordStatVO;
import com.uunnm.titletwo.business.record.vo.PersonEventCountVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RecordMapper extends BaseMapper<InputRecord> {
    @Select("""
            SELECT
                jt.keyword AS keyword,
                COUNT(*) AS total
            FROM tb_input_record t
            JOIN JSON_TABLE(
                t.keyword_tags,
                '$[*]' COLUMNS (
                    keyword VARCHAR(100) PATH '$'
                )
            ) AS jt
            WHERE t.is_deleted = 0
            GROUP BY jt.keyword
            ORDER BY total DESC
            """)
    List<KeywordStatVO> selectKeywordStats();

    @Select("""
            <script>
            SELECT
                party_name,
                party_phone,
                COUNT(*) AS event_count
            FROM tb_input_record AS t
            WHERE t.is_deleted = 0
            AND (
                <foreach collection="persons"
                    item="person"
                    separator=" OR "
                >
                (party_name = #{person.partyName}
                AND party_phone = #{person.partyPhone})
                </foreach>
            )
            GROUP BY party_name, party_phone
            </script>
            """)
    List<PersonEventCountVO> selectPersonEventCount(@Param("persons") List<PersonQueryBO> persons);
}
