package com.uunnm.titletwo.business.record.service.impl;

import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.uunnm.titletwo.business.record.entity.RepeatRecord;
import com.uunnm.titletwo.business.record.mapper.RepeatRecordMapper;
import com.uunnm.titletwo.business.record.service.RepeatRecordService;
import org.springframework.stereotype.Service;

@Service
public class RepeatRecordServiceImpl extends ServiceImpl<RepeatRecordMapper, RepeatRecord> implements RepeatRecordService {
    @Override
    public void add(RepeatRecord repeatRecord) {
        save(repeatRecord);
    }
}
