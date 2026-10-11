package com.uunnm.titletwo.business.risk.controller;

import com.uunnm.titletwo.business.risk.service.RiskService;
import com.uunnm.titletwo.business.risk.vo.RiskVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("risk")
@RequiredArgsConstructor
public class RiskController {
    private final RiskService riskService;

    @GetMapping("read")
    public RiskVO read(@RequestParam Long eventId){
        return riskService.read(eventId);
    }
}
