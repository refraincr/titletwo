package com.uunnm.titletwo.business.record.controller;


import com.uunnm.titletwo.business.record.service.RecordService;
import com.uunnm.titletwo.business.record.bo.RecordAddBO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("record")
@RequiredArgsConstructor
public class RecordController {
    private RecordService recordService;

    @PostMapping("add")
    public void add(@RequestBody RecordAddBO recordAddBO){
        recordService.add(recordAddBO);
    }
}
