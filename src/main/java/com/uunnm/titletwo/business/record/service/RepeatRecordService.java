package com.uunnm.titletwo.business.record.service;

import com.baomidou.mybatisplus.spring.service.IService;
import com.uunnm.titletwo.business.record.entity.RepeatRecord;

import java.util.List;

public interface RepeatRecordService extends IService<RepeatRecord> {
    void add(RepeatRecord repeatRecord);
    void addBatch(List<RepeatRecord> repeatRecords);
    List<Long> page();
}
