package com.uunnm.titletwo.business.risk.service.Impl;

import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.uunnm.titletwo.business.risk.bo.RiskAddBO;
import com.uunnm.titletwo.business.risk.entity.RiskCalculationBasis;
import com.uunnm.titletwo.business.risk.mapper.RiskMapper;
import com.uunnm.titletwo.business.risk.service.RiskService;
import com.uunnm.titletwo.business.risk.vo.RiskVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class RiskServiceImpl extends ServiceImpl<RiskMapper, RiskCalculationBasis> implements RiskService {
    @Override
    public void input(RiskAddBO riskAddBO) {
        RiskCalculationBasis calculationBasis = new RiskCalculationBasis();
        BeanUtils.copyProperties(riskAddBO, calculationBasis);
        save(calculationBasis);
    }

    @Override
    public RiskVO read(Long eventId) {
        RiskCalculationBasis calculationBasis = getById(eventId);
        RiskVO riskVO = new RiskVO();
        BeanUtils.copyProperties(calculationBasis, riskVO);
        return riskVO;
    }
}
