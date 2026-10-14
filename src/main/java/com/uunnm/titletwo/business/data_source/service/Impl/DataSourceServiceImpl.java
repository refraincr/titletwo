package com.uunnm.titletwo.business.data_source.service.Impl;

import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.uunnm.titletwo.business.data_source.entity.DataSource;
import com.uunnm.titletwo.business.data_source.mapper.DataSourceMapper;
import com.uunnm.titletwo.business.data_source.service.DataSourceService;
import com.uunnm.titletwo.business.data_source.vo.DataSourceGetVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class DataSourceServiceImpl extends ServiceImpl<DataSourceMapper, DataSource> implements DataSourceService {
    @Override
    public DataSourceGetVO get(Long id) {
        DataSourceGetVO dataSourceGetVO = new DataSourceGetVO();
        DataSource dataSource = getById(id);
        BeanUtils.copyProperties(dataSource, dataSourceGetVO);
        return dataSourceGetVO;
    }
}
