package com.ustc.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * @author 叶嘉耘
 * @date 2021/6/20
 */
@Document(collection = "DiskFile")
public class DiskFile {
    @Id
    private ObjectId id;
    /**
     * 父目录id
     */
    private String pid;
    /**
     * 文件md5
     */
    private String fileMd5;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件大小
     */
    private Long fileSize;
    /**
     * 文件类型, 0为文件夹, 1为文件
     */
    private Integer fileType;
    /**
     * 文件类型码
     */
    private String typeCode;
    /**
     * 文件后缀
     */
    private String fileSuffix;
    /**
     * 用户id
     */
    private String userid;
    /**
     * 创建时间
     */
    private Date createTime;

    public ObjectId getId() {
        return id;
    }

    public DiskFile setId(ObjectId id) {
        this.id = id;
        return this;
    }

    public String getPid() {
        return pid;
    }

    public DiskFile setPid(String pid) {
        this.pid = pid;
        return this;
    }

    public String getFileMd5() {
        return fileMd5;
    }

    public DiskFile setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public DiskFile setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public DiskFile setFileSize(Long fileSize) {
        this.fileSize = fileSize;
        return this;
    }

    public Integer getFileType() {
        return fileType;
    }

    public DiskFile setFileType(Integer fileType) {
        this.fileType = fileType;
        return this;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public DiskFile setTypeCode(String typeCode) {
        this.typeCode = typeCode;
        return this;
    }

    public String getFileSuffix() {
        return fileSuffix;
    }

    public DiskFile setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix;
        return this;
    }

    public String getUserid() {
        return userid;
    }

    public DiskFile setUserid(String userid) {
        this.userid = userid;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public DiskFile setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
}
