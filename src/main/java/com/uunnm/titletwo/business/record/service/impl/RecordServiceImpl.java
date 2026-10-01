package com.uunnm.titletwo.business.record.service.impl;

import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.uunnm.titletwo.business.record.bo.RecordAddBO;
import com.uunnm.titletwo.business.record.entity.InputRecord;
import com.uunnm.titletwo.business.record.mapper.RecordMapper;
import com.uunnm.titletwo.business.record.service.RecordService;
import org.springframework.stereotype.Service;

@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper,InputRecord> implements RecordService {
    @Override
    public void add(RecordAddBO RecordAddBO) {

    }
}
