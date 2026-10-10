package com.uunnm.titletwo.business.record.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.uunnm.titletwo.business.record.entity.InputRecord;
import com.uunnm.titletwo.business.record.vo.KeywordStatVO;
import org.apache.ibatis.annotations.Mapper;
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
}
