package com.uunnm.titletwo.business.risk.service;

import com.baomidou.mybatisplus.spring.service.IService;
import com.uunnm.titletwo.business.risk.bo.RiskAddBO;
import com.uunnm.titletwo.business.risk.entity.RiskCalculationBasis;
import com.uunnm.titletwo.business.risk.vo.RiskVO;

public interface RiskService extends IService<RiskCalculationBasis> {
    void input(RiskAddBO riskAddBO);
    RiskVO read(Long eventId);
}
