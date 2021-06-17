package com.ustc.upload.service;

public interface UploadStoreService {
    /**
     * 上传文件
     * @param bytes
     * @param filename
     * @return
     */
    public String upload(byte[] bytes, String filename);
}
