package com.uunnm.titletwo.business.record.service;

import com.baomidou.mybatisplus.spring.service.IService;
import com.uunnm.titletwo.business.record.bo.RecordAddBO;
import com.uunnm.titletwo.business.record.bo.RecordEditBO;
import com.uunnm.titletwo.business.record.bo.RecordQueryBO;
import com.uunnm.titletwo.business.record.entity.InputRecord;
import com.uunnm.titletwo.business.record.vo.KeywordStatVO;
import com.uunnm.titletwo.business.record.vo.RecordQueryVO;
import com.uunnm.titletwo.common.entity.PageVO;

import java.util.List;


public interface RecordService extends IService<InputRecord> {
    void add(RecordAddBO RecordAddBO);

    void del(Long id);

    PageVO<RecordQueryVO> page(RecordQueryBO recordQueryBO);

    void edit(RecordEditBO editBO);

    Integer getCountByNameAndPhone(String name, String phone);

    List<KeywordStatVO> keywords();
}
