package com.ustc.upload.service;

import com.ustc.entity.FileSearchBean;
import com.ustc.entity.PageInfo;

/**
 * @author 叶嘉耘
 * @date 2021/6/27
 */
public interface FileSearchService {
    /**
     * 查找文件
     * @param filename 文件名
     * @param userid 用户id
     * @param page 页数
     * @param limit 每页记录数
     * @return 文件
     */
    PageInfo<FileSearchBean> search(String filename, String userid, Integer page, Integer limit) throws Exception;

    /**
     * 添加一项记录
     * @param bean 查找bean
     */
    void add(FileSearchBean bean);

    /**
     * 删除
     * @param id 文件id
     */
    void delete(String id);
    /**
     * 删除所有记录
     */
    void deleteAll();

    /**
     * 查找有多少条记录
     * @return 记录数
     */
    Long findCount();
}
