package com.ustc.upload.dao;

import com.ustc.entity.DiskMd5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * @author 叶嘉耘
 */
@Repository
public class DiskMd5Dao {
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 判断文件md5在文件md5集合中是否存在
     *
     * @param fileMd5 文件Md5
     * @return 存在返回true, 不存在返回false
     */
    public boolean findMd5IsExist(String fileMd5) {
        return mongoTemplate.exists(new Query(Criteria.where("fileMd5").is(fileMd5)), DiskMd5.class);
    }

    /**
     * 将一个diskMd5对象插入集合中
     *
     * @param diskMd5 DiskMd5文档实体对象
     */
    public void insertOne(DiskMd5 diskMd5) {
        mongoTemplate.insert(diskMd5);
    }


}
