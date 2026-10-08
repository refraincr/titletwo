package com.uunnm.titletwo.business.record.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.uunnm.titletwo.business.auth.service.UserService;
import com.uunnm.titletwo.business.auth.vo.UserInfoVO;
import com.uunnm.titletwo.business.record.entity.RepeatRecord;
import com.uunnm.titletwo.business.record.mapper.RepeatRecordMapper;
import com.uunnm.titletwo.business.record.service.RepeatRecordService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class RepeatRecordServiceImpl extends ServiceImpl<RepeatRecordMapper, RepeatRecord> implements RepeatRecordService {
    private UserService userService;

    @Override
    public void add(RepeatRecord repeatRecord) {
        save(repeatRecord);
    }

    @Override
    public void addBatch(List<RepeatRecord> repeatRecords) {
        saveBatch(repeatRecords);
    }

    @Override
    public List<Long> page() {
        UserInfoVO userInfo = userService.getUserInfo();
        String unit = userInfo.getUnit();
        LambdaQueryWrapper<RepeatRecord> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(RepeatRecord::getDepartment, unit);
        List<RepeatRecord> list = list(wrapper);
        List<Long> ids = new ArrayList<>();
        list.forEach(repeatRecord -> ids.add(repeatRecord.getId()));
        return ids;
    }
}
