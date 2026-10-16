package com.uunnm.titletwo.business.record.service;

import com.baomidou.mybatisplus.spring.service.IService;
import com.uunnm.titletwo.business.record.bo.RecordAddBO;
import com.uunnm.titletwo.business.record.bo.RecordEditBO;
import com.uunnm.titletwo.business.record.bo.RecordQueryBO;
import com.uunnm.titletwo.business.record.entity.InputRecord;
import com.uunnm.titletwo.business.record.vo.KeywordStatVO;
import com.uunnm.titletwo.business.record.vo.RecordQueryVO;
import com.uunnm.titletwo.common.entity.PageVO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface RecordService extends IService<InputRecord> {
    void input(RecordAddBO RecordAddBO);

    void del(Long id);

    PageVO<RecordQueryVO> page(RecordQueryBO recordQueryBO);

    void edit(RecordEditBO editBO);

    List<KeywordStatVO> keywords();

    List<RecordQueryVO> queryByKeywords(String keyword);

    void fileInput(MultipartFile file);

    void importTemplate(String type, HttpServletResponse response) throws IOException;
}
