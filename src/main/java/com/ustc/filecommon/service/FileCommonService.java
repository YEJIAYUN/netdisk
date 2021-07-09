package com.ustc.filecommon.service;

import com.ustc.entity.FileListBean;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;
import java.util.List;

/**
 * @author 叶嘉耘
 * @date 2021/7/9
 */
public interface FileCommonService {
    /**
     * 查找文件夹列表(用于文件移动和复制), 查找pid下所有的文件id不在idList下的文件夹
     * @param userid 用户id
     * @param pid 父文件夹id
     * @param idList 文件id列表
     * @return 返回文件夹列表
     */
    List<FileListBean> findFolderList(String userid, String pid, List<String> idList);

    /**
     * 用于文件移动, 文件移动到指定目录下
     * @param userid 用户id
     * @param pid 父文件夹id
     * @param idList 文件id列表
     */
    void move(String userid, String pid, List<String> idList) throws IOException;

    /**
     * 修改文件名
     * @param userid 用户id
     * @param id 文件id
     * @param newName 新文件名
     */
    void rename(String userid, String id, String newName);
}
