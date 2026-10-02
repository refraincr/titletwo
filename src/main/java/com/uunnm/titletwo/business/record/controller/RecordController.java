package com.uunnm.titletwo.business.record.controller;


import com.uunnm.titletwo.business.record.bo.RecordQueryBO;
import com.uunnm.titletwo.business.record.service.RecordService;
import com.uunnm.titletwo.business.record.bo.RecordAddBO;
import com.uunnm.titletwo.business.record.vo.RecordQueryVO;
import com.uunnm.titletwo.common.entity.PageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("record")
@RequiredArgsConstructor
public class RecordController {
    private final RecordService recordService;

    @PostMapping("add")
    public void add(@RequestBody @Validated RecordAddBO recordAddBO){
        recordService.add(recordAddBO);
    }

    @PostMapping("del")
    public void del(@RequestParam Long id){
        recordService.del(id);
    }

    @GetMapping("page")
    public PageVO<RecordQueryVO> page(@RequestBody RecordQueryBO recordQueryBO) {
        return recordService.page(recordQueryBO);
    }
}
