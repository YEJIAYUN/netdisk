package com.ustc.entity;

import java.io.Serializable;

/**
 * @author 叶嘉耘
 */
public class MergeFileBean implements Serializable {
    /**
     * 文件夹pid, 上传到哪个文件夹
     */
    private String pid;
    /**
     * 前端上传uuid
     */
    private String uuid;
    /**
     * 前端上传文件id
     */
    private String fileId;
    /**
     * 文件名
     */
    private String filename;
    /**
     * 文件大小
     */
    private long totalSize;
    /**
     * 文件md5
     */
    private String filemd5;
    /**
     * 用于文件夹上传时,保存相对路径
     */
    private String relativepath;
    /**
     * 用户id
     */
    private String userid;
    /**
     * 用户名
     */
    private String username;

    public String getPid() {
        return pid;
    }

    public MergeFileBean setPid(String pid) {
        this.pid = pid;
        return this;
    }

    public String getUuid() {
        return uuid;
    }

    public MergeFileBean setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getFileId() {
        return fileId;
    }

    public MergeFileBean setFileId(String fileId) {
        this.fileId = fileId;
        return this;
    }

    public String getFilename() {
        return filename;
    }

    public MergeFileBean setFilename(String filename) {
        this.filename = filename;
        return this;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public MergeFileBean setTotalSize(long totalSize) {
        this.totalSize = totalSize;
        return this;
    }

    public String getFilemd5() {
        return filemd5;
    }

    public MergeFileBean setFilemd5(String filemd5) {
        this.filemd5 = filemd5;
        return this;
    }

    public String getRelativepath() {
        return relativepath;
    }

    public MergeFileBean setRelativepath(String relativepath) {
        this.relativepath = relativepath;
        return this;
    }

    public String getUserid() {
        return userid;
    }

    public MergeFileBean setUserid(String userid) {
        this.userid = userid;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public MergeFileBean setUsername(String username) {
        this.username = username;
        return this;
    }
}
