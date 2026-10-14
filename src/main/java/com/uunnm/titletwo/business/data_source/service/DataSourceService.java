package com.uunnm.titletwo.business.data_source.service;

import com.baomidou.mybatisplus.spring.service.IService;
import com.uunnm.titletwo.business.data_source.entity.DataSource;
import com.uunnm.titletwo.business.data_source.vo.DataSourceGetVO;

public interface DataSourceService extends IService<DataSource> {
    DataSourceGetVO get(Long id);
}
