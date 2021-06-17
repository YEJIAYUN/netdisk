package com.ustc.upload.service.impl;

import com.ustc.chain.core.HandlerInitializer;
import com.ustc.chain.core.Pipeline;
import com.ustc.chain.core.ResponsibleChain;
import com.ustc.chain.handler.ChunkFileSuffixHandler;
import com.ustc.chain.handler.ChunkRedisHandler;
import com.ustc.chain.handler.ChunkStoreHandler;
import com.ustc.chain.handler.ChunkValidateHandler;
import com.ustc.chain.param.ChunkRequest;
import com.ustc.entity.Chunk;
import com.ustc.upload.service.UploadFileService;
import com.ustc.utils.SpringContentUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 叶嘉耘
 */
@Component
@Transactional
public class UploadFileServiceImpl implements UploadFileService {
    @Autowired
    private SpringContentUtils scu;

    @Override
    public void uploadChunk(Chunk chunk) {
        // 1. 参数转换, 将chunk转换为责任链中的Request
        ChunkRequest chunkRequest = new ChunkRequest();
        BeanUtils.copyProperties(chunk, chunkRequest);
        // 2. 创建责任链的启动类
        ResponsibleChain responsibleChain = new ResponsibleChain();
        // 3. 组装责任链
        responsibleChain.loadHandler(new HandlerInitializer(chunkRequest, null) {
            @Override
            protected void initChannel(Pipeline line) {
                // 3.1 参数校验
                line.addLast(scu.getHandler(ChunkValidateHandler.class));
                // 3.2 校验是否支持对应格式
                // line.addLast(new ChunkFileSuffixHandler());
                // 3.3 切块存储
                line.addLast(scu.getHandler(ChunkStoreHandler.class));
                // 3.4 保存记录至Redis
                line.addLast(scu.getHandler(ChunkRedisHandler.class));
            }
        });

        // 4. 执行责任链
        responsibleChain.execute();
    }

    @Override
    public Integer checkFile(String fileMd5) {
        return null;
    }
}
