package com.ustc.filecommon.service.impl;

import com.ustc.chain.core.HandlerInitializer;
import com.ustc.chain.core.Pipeline;
import com.ustc.chain.core.ResponsibleChain;
import com.ustc.chain.handler.MoveUpdateHandler;
import com.ustc.chain.handler.MoveValidateHandler;
import com.ustc.chain.param.MoveRequest;
import com.ustc.entity.DiskFile;
import com.ustc.entity.FileListBean;
import com.ustc.filecommon.dao.FileDao;
import com.ustc.filecommon.service.FileCommonService;
import com.ustc.utils.SpringContentUtils;
import org.apache.http.client.utils.DateUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 叶嘉耘
 * @date 2021/7/9
 */
@Service
public class FileCommonServiceImpl implements FileCommonService {
    @Autowired
    FileDao fileDao;

    @Resource(name = "springContentUtils")
    SpringContentUtils scu;

    @Override
    public List<FileListBean> findFolderList(String userid, String pid, List<String> idList) {
        List<DiskFile> folders = fileDao.findFolders(userid, pid, idList);
        List<FileListBean> folderList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(folders)) {
            for (DiskFile diskFile : folders) {
                FileListBean fileListBean = new FileListBean();
                fileListBean.setId(diskFile.getId().toString());
                fileListBean.setPid(diskFile.getPid());
                fileListBean.setFilename(diskFile.getFileName());
                fileListBean.setCreatetime(DateUtils.formatDate(diskFile.getCreateTime(), "yyyy-MM-dd HH:mm"));
                // 将文件夹bean加入列表中
                folderList.add(fileListBean);
            }
        }
        return folderList;
    }

    @Override
    public void move(String userid, String pid, List<String> idList) throws IOException {
        ResponsibleChain responsibleChain = new ResponsibleChain();
        MoveRequest moveRequest = new MoveRequest(userid, pid, idList);

        responsibleChain.loadHandler(new HandlerInitializer(moveRequest, null) {
            @Override
            protected void initChannel(Pipeline line) {
                // 1. 参数校验
                line.addLast(scu.getHandler(MoveValidateHandler.class));
                // 2. 移动
                line.addLast(scu.getHandler(MoveUpdateHandler.class));
            }
        });
        responsibleChain.execute();
    }

    @Override
    public void rename(String userid, String id, String newName) {

    }
}
