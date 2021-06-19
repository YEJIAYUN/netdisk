package com.ustc.chain.handler;

import com.ustc.chain.core.ContextRequest;
import com.ustc.chain.core.ContextResponse;
import com.ustc.chain.core.Handler;
import com.ustc.chain.param.ChunkRequest;
import com.ustc.exception.ServiceException;
import com.ustc.exception.ServiceExceptionEnum;
import org.springframework.stereotype.Component;

/**
 * 将切块存储到Redis
 * @author 叶嘉耘
 */
@Component
public class ChunkRedisHandler extends Handler {
    @Override
    public void doHandler(ContextRequest request, ContextResponse response) {
        if (request instanceof ChunkRequest) {
            ChunkRequest chunk = (ChunkRequest) request;

        } else {
            throw new ServiceException(ServiceExceptionEnum.PARAM_ERROR);
        }
    }
}
