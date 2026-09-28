package com.uunnm.titletwo.common.log.controller;

import com.uunnm.titletwo.common.entity.ResultWrapper;
import com.uunnm.titletwo.common.log.bo.OperationLogQueryBO;
import com.uunnm.titletwo.common.log.service.OperationLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 操作日志查询接口。本票不做独立前端页面，接口供后续功能票复用
 * （如档案详情页"操作历史"、规则管理页"变更记录"，均通过 objectType+objectId 过滤）。
 * 注意：ResultWrapper 的具体包装方式（静态工厂方法名等）请按你项目里
 * common.entity.ResultWrapper 的真实定义核对，这里假设有 ResultWrapper.success(data)。
 */
@RestController
@RequestMapping("/api/operation-log")
@RequiredArgsConstructor
public class OperationLogController {

    private final OperationLogService operationLogService;

    @PostMapping("/page")
    public ResultWrapper<Object> page(@RequestBody OperationLogQueryBO query) {
        return ResultWrapper
                .success()
                .data(operationLogService.page(query));
    }
}