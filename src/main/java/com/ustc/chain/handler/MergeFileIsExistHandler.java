package com.ustc.chain.handler;

import com.ustc.chain.core.ContextRequest;
import com.ustc.chain.core.ContextResponse;
import com.ustc.chain.core.Handler;
import com.ustc.chain.param.MergeRequest;
import com.ustc.exception.ServiceException;
import com.ustc.exception.ServiceExceptionEnum;
import com.ustc.upload.dao.DiskMd5Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 检验文件是否存在
 * @author 叶嘉耘
 */
@Component
public class MergeFileIsExistHandler extends Handler {

    @Autowired
    private DiskMd5Dao diskMd5Dao;

    @Override
    public void doHandler(ContextRequest request, ContextResponse response) {
        if (request instanceof MergeRequest) {
            MergeRequest bean = (MergeRequest) request;

            boolean isExist = diskMd5Dao.findMd5IsExist(bean.getFilemd5());
            bean.setExistInDiskmd5(isExist);

            this.updateRequest(bean);
        } else {
            throw new ServiceException(ServiceExceptionEnum.UPLOAD_PARAM_ERROR);
        }
    }
}
