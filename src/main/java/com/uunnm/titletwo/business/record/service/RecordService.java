package com.uunnm.titletwo.business.record.service;

import com.baomidou.mybatisplus.spring.service.IService;
import com.uunnm.titletwo.business.record.bo.RecordAddBO;
import com.uunnm.titletwo.business.record.entity.InputRecord;

public interface RecordService extends IService<InputRecord> {
    void add(RecordAddBO RecordAddBO);
}
