package com.uunnm.titletwo.business.record.service.impl;

import com.uunnm.titletwo.business.record.service.RecordService;
import com.uunnm.titletwo.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 为了确保在事务完成后释放锁，编写此类
 */
@Service
@RequiredArgsConstructor
public class FileInputService {
    private final RecordService recordService;
    private final ReentrantLock importLock = new ReentrantLock();

    public void importFile(MultipartFile file) {
        if (!importLock.tryLock()) {
            throw new BusinessException("当前有文件正在导入，请稍后重试");
        }

        try {
            recordService.fileInput(file);
        } finally {
            importLock.unlock();
        }
    }
}
