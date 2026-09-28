package com.uunnm.titletwo.common.entity;

import lombok.Data;

import java.util.List;

@Data
public class PageVO<T> {
    private Long currentPage = 0L;
    private Long pageSize = 10L;
    private Long totalSize = 0L;
    private List<T> dataList;
}
