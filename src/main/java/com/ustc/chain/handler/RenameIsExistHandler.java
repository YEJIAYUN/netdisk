package com.ustc.chain.handler;

import com.ustc.chain.core.ContextRequest;
import com.ustc.chain.core.ContextResponse;
import com.ustc.chain.core.Handler;
import com.ustc.chain.param.RenameRequest;
import com.ustc.entity.DiskFile;
import com.ustc.exception.ServiceException;
import com.ustc.exception.ServiceExceptionEnum;
import com.ustc.filecommon.dao.FileDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author 叶嘉耘
 * @date 2021/7/9
 */
@Component
public class RenameIsExistHandler extends Handler {

    @Autowired
    private FileDao fileDao;

    @Override
    public void doHandler(ContextRequest request, ContextResponse response) throws IOException {
        if (request instanceof RenameRequest) {
            RenameRequest renameRequest = (RenameRequest) request;
            String id = renameRequest.getId();
            String userid = renameRequest.getUserid();
            String newName = renameRequest.getNewName();

            DiskFile diskFile = fileDao.findOne(userid, id);
            if (diskFile == null) {
                throw new ServiceException(ServiceExceptionEnum.FILE_NOT_EXIST);
            }
            // 判断是否存在同名文件
            if (fileDao.findIsExistBySameNameRecord(userid, diskFile.getPid(), id, newName, diskFile.getFileType())) {
                throw new ServiceException(ServiceExceptionEnum.SAME_NAME_EXIST);
            }

        } else {
            throw new ServiceException(ServiceExceptionEnum.RENAME_PARAM_ERROR);
        }
    }
}
