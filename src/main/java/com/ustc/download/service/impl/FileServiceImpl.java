package com.ustc.download.service.impl;

import com.ustc.download.dao.FileListRepository;
import com.ustc.download.service.FileService;
import com.ustc.entity.*;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.ustc.login.service.UserService;
import com.ustc.upload.dao.DiskFileDao;
import com.ustc.utils.CapacityUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Component
public class FileServiceImpl implements FileService {
    @Autowired
    private FileListRepository fileListRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private DiskFileDao diskFileDao;

    @Autowired
    private UserService userService;

    /**
     * @param pageIndex
     * @param pageSize
     * @param userid
     * @param pid
     * @param typecode
     * @param orderfield
     * @param ordertype
     * @return
     */
    @Override
    public PageInfo<FileListBean> findPageList(Integer pageIndex, Integer pageSize, String userid, String pid, String typecode, String orderfield, String ordertype) {
        PageInfo<FileListBean> pageInfo = new PageInfo<>();

        Query query = new Query(Criteria.where("userid").is(userid));
        query.addCriteria(Criteria.where("pid").is(pid));
        Sort sort = Sort.by(Sort.Order.desc("createTime"));
        Pageable pageable = PageRequest.of(pageIndex - 1, pageSize, sort);
        query.with(pageable);
        List<DiskFile> page = mongoTemplate.find(query, DiskFile.class);
        List<DiskFile> all = mongoTemplate.findAll(DiskFile.class);
        ArrayList<FileListBean> rows = new ArrayList<>();

        for (DiskFile diskFile : page) {
            FileListBean fileListBean = new FileListBean();


            fileListBean.setId(diskFile.getId().toString());
            fileListBean.setPid(diskFile.getPid());
            if (!"0".equals(diskFile.getPid())) {
                fileListBean.setPname(findOne(diskFile.getPid()).getFilename());
            } else {
                fileListBean.setPname("");
            }
            fileListBean.setFilename(diskFile.getFileName());
            fileListBean.setFilesize(diskFile.getFileSize());
            fileListBean.setFilesizename(CapacityUtils.convert(diskFile.getFileSize()));
            fileListBean.setFilesuffix(diskFile.getFileSuffix());
            fileListBean.setFilemd5(diskFile.getFileMd5());
            fileListBean.setFiletype(diskFile.getFileType());
            fileListBean.setCreateuserid(diskFile.getUserid());
//            fileListBean.setCreateusername(userService.findOne(diskFile.getUserid()).getUsername());
            fileListBean.setCreateusername("test");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            fileListBean.setCreatetime(simpleDateFormat.format(diskFile.getCreateTime()));

            rows.add(fileListBean);
        }
        pageInfo.setTotalPage((all.size() % pageSize) == 0 ? (long) (all.size() / pageSize) : (long) (all.size() / pageSize + 1))
        ;
        pageInfo.setPage(pageIndex);
        pageInfo.setLimit(pageSize);
        pageInfo.setTotalElements((long) all.size());
        pageInfo.setRows(rows);
        return pageInfo;
    }

    @Override
    public List<DiskFile> findAllFile() {
        return this.fileListRepository.findAll();
    }

    @Override
    public FileBean findOne(String id) {
        FileBean fileBean;
        Query query = Query.query(Criteria.where("_id").is(new ObjectId(id)));
        DiskFile targetFile = mongoTemplate.findOne(query, DiskFile.class);
        fileBean = diskFileToFileBean(targetFile);
        return fileBean;
    }

    /**
     * ???DiskFile????????????FileBean???
     *
     * @param diskFile
     * @return
     */
    public FileBean diskFileToFileBean(DiskFile diskFile) {
        FileBean fileBean = new FileBean();
        fileBean.setId(diskFile.getId().toString());
        // ???fileBean??????id??????diskFile??????id
        fileBean.setPid(diskFile.getPid());
        // ?????????id??????????????????????????????, ?????????id==0, ?????????????????????????????????
        String pName;
        Query queryPname = Query.query(Criteria.where("_id").is(diskFile.getPid()));
        DiskFile pFile = this.mongoTemplate.findOne(queryPname, DiskFile.class);
        pName = pFile == null ? "" : pFile.getFileName();
        fileBean.setPname(pName);
        fileBean.setFilename(diskFile.getFileName());
        fileBean.setUploadDate(diskFile.getCreateTime());
        fileBean.setFileSuffix(diskFile.getFileSuffix());
        fileBean.setFilesize(diskFile.getFileSize());
        fileBean.setUploadUserId(diskFile.getUserid());
        fileBean.setFilemd5(diskFile.getFileMd5());
        fileBean.setFiletype(diskFile.getFileType());
        Query queryUserName = Query.query((Criteria.where("username").is(diskFile.getUserid())));
        UserDO user = mongoTemplate.findOne(queryUserName, UserDO.class);
        fileBean.setUploadUserName(user.getUsername());

        return fileBean;
    }


    @Override
    public List<FileBean> findChildrenFiles(String userId, String folderId) {
        return findChildrenFiles(folderId);
    }


    @Override
    public List<FileBean> findChildrenFiles(String folderId) {
        List<FileBean> children = new ArrayList<>();
        Query query = Query.query(Criteria.where("pid").is(folderId));
        List<DiskFile> files = this.mongoTemplate.find(query, DiskFile.class);
        if (!files.isEmpty()) {
            for (DiskFile file : files) {
                children.add(diskFileToFileBean(file));
            }
        }
        return children;
    }

    @Override
    public List<String> getChunksByFilemd5(String fileMd5) {
        List<String> urls = new ArrayList<>();
        Query query = Query.query(Criteria.where("fileMd5").is(fileMd5));
        List<DiskMd5Chunk> chunks = this.mongoTemplate.find(query, DiskMd5Chunk.class);
        Collections.sort(chunks, new Comparator<DiskMd5Chunk>() {
            @Override
            public int compare(DiskMd5Chunk chunk1, DiskMd5Chunk chunk2) {
                return chunk1.getChunkNumber().intValue() - chunk2.getChunkNumber().intValue();
            }
        });
        Collections.sort(chunks, Comparator.comparingInt(DiskMd5Chunk::getChunkNumber));
        /*     */
        /* 108 */
        for (DiskMd5Chunk chunk : chunks) {
            /* 109 */
            urls.add(chunk.getStorePath());
            /*     */
        }
        /* 111 */
        return urls;
        /*     */
    }

    @Override
    public byte[] getBytesByUrl(String url) throws IOException {
        FileInputStream input = new FileInputStream(url);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        IOUtils.copyLarge(input, output);
        return output.toByteArray();
    }

    @Override
    public void saveFile(DiskFile file) {
        this.fileListRepository.save(file);
    }


    public void deleteFile(String filename) {
        Query query = Query.query((CriteriaDefinition) Criteria.where("filename").is(filename));
        this.mongoTemplate.remove(query, DiskFile.class);
    }

    @Override
    public DownloadBean getDownloadInfo(List<String> fileIds) {
        if (CollectionUtils.isEmpty(fileIds)) {
            throw new RuntimeException("?????????????????????");
        }
        DownloadBean bean = new DownloadBean();
        bean.setFileNum(0);
        bean.setFolderNum(0);
        bean.setTotalSize(0L);
        for (String fileId : fileIds) {
            Query query = Query.query(Criteria.where("_id").is(fileId));
            DiskFile file = this.mongoTemplate.findOne(query, DiskFile.class);
            bean.setTotalSize(bean.getTotalSize() + file.getFileSize());
            if (file.getFileType() == 1) {
                bean.setFileNum(bean.getFileNum() + 1);
            } else if (file.getFileType() == 0) {
                bean.setFolderNum(bean.getFolderNum() + 1);
            }
            dgGetDownloadInfo(file.getId().toString(), bean);
        }
        return bean;
    }

    private void dgGetDownloadInfo(String id, DownloadBean bean) {
        List<FileBean> childrenFiles = findChildrenFiles(id);
        if (!CollectionUtils.isEmpty(childrenFiles)) {
            for (FileBean file : childrenFiles) {
                bean.setTotalSize(bean.getTotalSize() + file.getFilesize());
                if (file.getFiletype() == 1) {
                    bean.setFileNum(bean.getFileNum() + 1);
                } else if (file.getFiletype() == 0) {
                    bean.setFolderNum(bean.getFolderNum() + 1);
                }
                dgGetDownloadInfo(file.getId(), bean);
            }
        }
    }
}