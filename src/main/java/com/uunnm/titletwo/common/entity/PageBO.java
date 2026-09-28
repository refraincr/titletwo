package com.uunnm.titletwo.common.entity;

import lombok.Data;

@Data
public class PageBO {
    private Long currentPage = 0L;
    private Long pageSize = 10L;
}
