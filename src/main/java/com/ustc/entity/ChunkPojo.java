package com.ustc.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author 叶嘉耘
 */
@ApiModel(description = "切块参数")
public class ChunkPojo {
    @ApiModelProperty(value = "前端生成的uuid", required = true)
    private String uuid;
    @ApiModelProperty(value = "前端生成的文件id", required = true)
    private String id;
    @ApiModelProperty(value = "文件名称", required = true)
    private String name;
    @ApiModelProperty(value = "文件大小", required = true)
    private String size;
    @ApiModelProperty(value = "切块序号", required = true)
    private Integer chunk;
    @ApiModelProperty(value = "切块数量", required = true)
    private Integer chunks;

    public String getUuid() {
        return uuid;
    }

    public ChunkPojo setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getId() {
        return id;
    }

    public ChunkPojo setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ChunkPojo setName(String name) {
        this.name = name;
        return this;
    }

    public String getSize() {
        return size;
    }

    public ChunkPojo setSize(String size) {
        this.size = size;
        return this;
    }

    public Integer getChunk() {
        return chunk;
    }

    public ChunkPojo setChunk(Integer chunk) {
        this.chunk = chunk;
        return this;
    }

    public Integer getChunks() {
        return chunks;
    }

    public ChunkPojo setChunks(Integer chunks) {
        this.chunks = chunks;
        return this;
    }
}