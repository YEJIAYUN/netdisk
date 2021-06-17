package com.ustc.upload.service;

import com.ustc.entity.Chunk;

/**
 * @author 叶嘉耘
 */
public interface UploadFileService {
    /**
     * 上传切块
     * @param chunk 切块
     */
    void uploadChunk(Chunk chunk);

    /**
     * 检查文件是否存在
     * @param fileMd5 文件的md5
     * @return 检查结果
     */
    Integer checkFile(String fileMd5);
}
