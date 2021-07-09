package com.ustc.filecommon.dao;

import com.ustc.entity.DiskFile;
import com.ustc.utils.FileType;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 叶嘉耘
 * @date 2021/7/8
 */
@Repository
public class FileDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 查询属于某用户的位于某文件夹下的与选中记录名称不同的的所有文件夹
     *
     * @param userid 用户id
     * @param pid    父文件夹id
     * @param idList 记录id列表
     * @return 文件夹列表
     */
    public List<DiskFile> findFolders(String userid, String pid, List<String> idList) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userid").is(userid));
        query.addCriteria(Criteria.where("pid").is(pid));
        query.addCriteria(Criteria.where("_id").nin(idList));
        query.addCriteria(Criteria.where("fileType").is(FileType.FOLDER.getTypeCode()));
        // 根据创建时间降序
        query.with(Sort.by(Sort.Order.desc("createTime")));

        return mongoTemplate.find(query, DiskFile.class);
    }

    /**
     * 根据文件id查询一条记录
     *
     * @param userid 用户id
     * @param id     文件id
     * @return 一条记录
     */
    public DiskFile findOne(String userid, String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userid").is(userid));
        query.addCriteria(Criteria.where("_id").is(new ObjectId(id)));
        return mongoTemplate.findOne(query, DiskFile.class);
    }

    /**
     * 根据父文件夹id和文件名查询记录
     *
     * @param userid   用户id
     * @param pid      父文件夹id
     * @param fileName 文件名
     * @return 一条记录
     */
    public DiskFile findRecordByPidAndName(String userid, String pid, String fileName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userid").is(userid));
        query.addCriteria(Criteria.where("pid").is(pid));
        query.addCriteria(Criteria.where("fileName").is(fileName));
        return mongoTemplate.findOne(query, DiskFile.class);
    }

    /**
     * @param record
     */
    public void updateRecord(DiskFile record) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(record.getId()));
        Update update = new Update();
        update.set("pid", record.getPid());
        update.set("fileName", record.getFileName());
        mongoTemplate.updateFirst(query, update, DiskFile.class);
    }

    /**
     * 寻找同一目录下是否存在同名文件, 需要排除自身
     *
     * @param userid   用户id
     * @param pid      父文件夹id
     * @param id       文件id
     * @param filename 文件名
     * @param fileType 文件类型
     * @return 是否存在
     */
    public boolean findIsExistBySameNameRecord(String userid, String pid, String id, String filename, Integer fileType) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userid").is(userid));
        query.addCriteria(Criteria.where("pid").is(pid));
        query.addCriteria(Criteria.where("_id").ne(new ObjectId(id)));
        query.addCriteria(Criteria.where("fileName").is(filename));
        query.addCriteria(Criteria.where("fileType").is(fileType));

        return mongoTemplate.exists(query, DiskFile.class);
    }

    /**
     * 更新记录文件名
     *
     * @param id 文件id
     * @param newName 新文件名
     */
    public void updateRecordFileName(String id, String newName) {
        mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(new ObjectId(id))),
                Update.update("fileName", newName), DiskFile.class);
    }
}
