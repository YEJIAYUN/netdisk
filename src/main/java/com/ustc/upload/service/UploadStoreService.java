package com.ustc.upload.service;

public interface UploadStoreService {
    /**
     * 上传文件
     * @param bytes 文件二进制数据
     * @param filename 文件名
     * @return 上传路径
     */
    String upload(byte[] bytes, String filename);
}
