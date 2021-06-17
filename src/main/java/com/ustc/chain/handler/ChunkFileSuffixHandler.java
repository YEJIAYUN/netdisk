package com.ustc.chain.handler;

import com.ustc.chain.core.ContextRequest;
import com.ustc.chain.core.ContextResponse;
import com.ustc.chain.core.Handler;
import com.ustc.chain.param.ChunkRequest;
import com.ustc.exception.ServiceException;
import com.ustc.exception.ServiceExceptionEnum;
import org.apache.commons.io.FilenameUtils;

/**
 * 校验文件格式
 * @author 叶嘉耘
 */
public class ChunkFileSuffixHandler extends Handler {
    @Override
    public void doHandler(ContextRequest request, ContextResponse response) {
//        if (request instanceof ChunkRequest) {
//            ChunkRequest chunk = (ChunkRequest) request;
//
//            // 获取文件名后缀
//            String suffix = FilenameUtils.getExtension(chunk.getName());
//
//            // 去Redis查询, 如果不存在, 去mysql查询

//        } else {
//            throw new RuntimeException("ChunkFileSuffixHandler参数不正确");
//        }
    }
}
