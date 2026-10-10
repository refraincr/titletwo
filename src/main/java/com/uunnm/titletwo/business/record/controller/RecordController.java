package com.uunnm.titletwo.business.record.controller;


import com.uunnm.titletwo.business.record.bo.RecordEditBO;
import com.uunnm.titletwo.business.record.bo.RecordQueryBO;
import com.uunnm.titletwo.business.record.service.RecordService;
import com.uunnm.titletwo.business.record.bo.RecordAddBO;
import com.uunnm.titletwo.business.record.vo.KeywordStatVO;
import com.uunnm.titletwo.business.record.vo.RecordQueryVO;
import com.uunnm.titletwo.common.entity.PageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("record")
@RequiredArgsConstructor
public class RecordController {
    private final RecordService recordService;

    @PostMapping("add")
    public void add(@RequestBody @Validated RecordAddBO recordAddBO){
        recordService.add(recordAddBO);
    }

    @PostMapping("input")
    public void input(@RequestBody @Validated RecordAddBO recordAddBO) {
        recordService.input(recordAddBO);
    }

    @DeleteMapping("del")
    public void del(@RequestParam Long id){
        recordService.del(id);
    }

    @GetMapping("page")
    public PageVO<RecordQueryVO> page(RecordQueryBO recordQueryBO) {
        return recordService.page(recordQueryBO);
    }

    @PutMapping("edit")
    public void edit(@RequestBody RecordEditBO editBO) {
        recordService.edit(editBO);
    }

    @GetMapping("keywords")
    public List<KeywordStatVO> keywords() {
        return recordService.keywords();
    }

    @GetMapping("queryByKeywords")
    public List<RecordQueryVO> queryByKeywords(@RequestParam String keyword) {
        return recordService.queryByKeywords(keyword);
    }
}
