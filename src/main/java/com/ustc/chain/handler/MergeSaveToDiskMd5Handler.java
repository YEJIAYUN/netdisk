package com.ustc.chain.handler;

import com.ustc.chain.core.ContextRequest;
import com.ustc.chain.core.ContextResponse;
import com.ustc.chain.core.Handler;
import com.ustc.chain.param.MergeRequest;
import com.ustc.entity.DiskMd5;
import com.ustc.entity.RedisChunkTemp;
import com.ustc.exception.ServiceException;
import com.ustc.exception.ServiceExceptionEnum;
import com.ustc.upload.dao.DiskMd5Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 将文件信息保存至DiskMd5表中
 * @author 叶嘉耘
 */
@Component
public class MergeSaveToDiskMd5Handler extends Handler {

    @Autowired
    private DiskMd5Dao diskMd5Dao;

    @Override
    public void doHandler(ContextRequest request, ContextResponse response) {
        if (request instanceof MergeRequest) {
            MergeRequest bean = (MergeRequest) request;

            // 如果在DiskMd5表中不存在
            if (!bean.isExistInDiskmd5()) {
                RedisChunkTemp redisChunkTemp = bean.getChunkTemps().get(0);
                // 设置DiskMd5参数
                // 存入集合中的文档不设id, 使用mongoDB自带的ObjectId
                DiskMd5 diskMd5 = new DiskMd5();
                diskMd5.setFileName(redisChunkTemp.getName());
                diskMd5.setFileMd5(bean.getFilemd5());
                diskMd5.setFileNum(redisChunkTemp.getChunks());
                diskMd5.setFileSuffix(redisChunkTemp.getFilesuffix());
                diskMd5.setFileSize(redisChunkTemp.getSize());
                diskMd5.setTypeCode("0");
                // 插入表中
                diskMd5Dao.insertOne(diskMd5);

                updateRequest(bean);
            }
        } else {
            throw new ServiceException(ServiceExceptionEnum.PARAM_ERROR);
        }
    }
}
