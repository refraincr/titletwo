package com.uunnm.titletwo.business.auth.service.impl;

import com.uunnm.titletwo.business.auth.constant.Units;
import com.uunnm.titletwo.business.auth.service.UnitService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UnitServiceImpl implements UnitService {
    @Override
    public List<String> query(String name) {
        return Arrays.stream(Units.values())
                .map(Units::getName)
                .filter(e->e.contains(name))
                .toList();
    }
}
