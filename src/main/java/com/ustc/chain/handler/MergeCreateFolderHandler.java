package com.ustc.chain.handler;

import com.ustc.chain.core.ContextRequest;
import com.ustc.chain.core.ContextResponse;
import com.ustc.chain.core.Handler;
import com.ustc.chain.param.MergeRequest;
import com.ustc.entity.DiskFile;
import com.ustc.exception.ServiceException;
import com.ustc.exception.ServiceExceptionEnum;
import com.ustc.upload.dao.DiskFileDao;
import com.ustc.utils.FileType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 判断是否为文件夹上传, 如果是文件夹上传则会创建文件夹
 */
@Component
public class MergeCreateFolderHandler extends Handler {

    @Autowired
    private DiskFileDao diskFileDao;

    @Override
    public void doHandler(ContextRequest request, ContextResponse response) {
        if (request instanceof MergeRequest) {
            MergeRequest bean = (MergeRequest) request;
            String relativePath = bean.getRelativepath();

            if (!StringUtils.isEmpty(relativePath)) {
                String[] names = relativePath.split("/");
                String userid = bean.getUserid();
                String pid = bean.getPid();

                List<DiskFile> folders = new ArrayList<>();
                // 为每一个路径创建一个文件夹, 最后一个为文件名, 不包括在内
                for (int i = 0; i < names.length - 1; i++) {
                    String name = names[i];
                    // 先判断是否存在该文件夹
                    DiskFile diskFile = diskFileDao.findFolder(userid, pid, name);

                    // 如果不存在该文件夹, 则创建该文件夹, 否则不需要创建
                    if (diskFile == null) {
                        DiskFile folder = new DiskFile();
                        folder.setFileType(FileType.FOLDER.getTypeCode());
                        folder.setFileName(name);
                        folder.setPid(pid);
                        folder.setUserid(userid);
                        folder.setFileSize(0L);
                        folder.setCreateTime(new Date());
                        // 给文件夹设置个随机uuid
                        folder.setId(UUID.randomUUID().toString());
                        diskFileDao.insertOne(folder);
                    }

                    // 将当前文件夹的id设置为新的pid
                    assert diskFile != null;
                    pid = diskFile.getId();
                    // 将对应的文件夹对象加入到
                    folders.add(diskFile);
                }
                // 更新当前文件的pid
                bean.setPid(pid);
                bean.setFolders(folders);
                this.updateRequest(bean);

            }
        } else {
            throw new ServiceException(ServiceExceptionEnum.PARAM_ERROR);
        }
    }
}