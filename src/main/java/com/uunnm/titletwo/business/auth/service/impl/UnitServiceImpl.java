package com.uunnm.titletwo.business.auth.service.impl;

import com.uunnm.titletwo.business.auth.constant.UnitsData;
import com.uunnm.titletwo.business.auth.service.UnitService;
import com.uunnm.titletwo.business.auth.vo.UnitsVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitServiceImpl implements UnitService {
    @Override
    public List<UnitsVO> query() {
        return UnitsData.UNITS;
    }
}
