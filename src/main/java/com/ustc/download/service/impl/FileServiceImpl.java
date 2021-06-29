package com.ustc.download.service.impl;

import com.ustc.download.dao.FileListRepository;
import com.ustc.download.service.FileService;
import com.ustc.entity.*;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.ustc.login.service.UserService;
import com.ustc.utils.CapacityUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private FileListRepository fileListRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private UserService userService;

    public PageInfo<FileListBean> findPageList(){
        PageInfo<FileListBean> pageInfo = new PageInfo<>();

        List<DiskFile> allFile = findAllFile();
        for (DiskFile diskFile : allFile) {
            FileListBean fileListBean = new FileListBean();

            // set
            fileListBean.setId(diskFile.getId());
            fileListBean.setPid(diskFile.getPid());
            fileListBean.setPname(findOne(diskFile.getId()).getPname());
            fileListBean.setFilename(diskFile.getFileName());
            fileListBean.setFilesize(diskFile.getFileSize());
            fileListBean.setFilesizename(CapacityUtils.convert(diskFile.getFileSize()));
            fileListBean.setFilesuffix(diskFile.getFileSuffix());
            fileListBean.setFilemd5(diskFile.getFileMd5());
            fileListBean.setFiletype(diskFile.getFileType());
            fileListBean.setCreateuserid(diskFile.getUserid());
            fileListBean.setCreateusername(userService.findOne(diskFile.getUserid()).getUsername());
            fileListBean.setCreatetime(diskFile.getCreateTime());

            pageInfo.getRows().add(fileListBean);
        }
        return pageInfo;
    }

    public List<DiskFile> findAllFile() {
        /*  32 */     return this.fileListRepository.findAll();
        /*     */   }

    public FileBean findOne(String id) {
        FileBean fileBean = new FileBean();

        Query query = Query.query((CriteriaDefinition)Criteria.where("id").is(id));
        DiskFile targetFile = (DiskFile)this.mongoTemplate.findOne(query, DiskFile.class);

        fileBean = diskFileToFileBean(targetFile);
        return fileBean;
    }
/*     */   public FileBean diskFileToFileBean(DiskFile diskFile) {
/*  47 */     FileBean fileBean = new FileBean();
/*     */     
/*  49 */     fileBean.setId(diskFile.getId());
/*  50 */     fileBean.setPid(diskFile.getPid());
/*     */     
/*  52 */     Query queryPname = Query.query((CriteriaDefinition)Criteria.where("id").is(diskFile.getPid()));
/*  53 */     DiskFile pFile = (DiskFile)this.mongoTemplate.findOne(queryPname, DiskFile.class);
/*  54 */     fileBean.setPname(pFile.getFileName());
/*     */     
/*  56 */     fileBean.setFilename(diskFile.getFileName());
/*  57 */     fileBean.setUploadDate(diskFile.getCreateTime());
/*  58 */     fileBean.setFileSuffix(diskFile.getFileSuffix());
/*  59 */     fileBean.setFilesize(diskFile.getFileSize().longValue());
/*  60 */     fileBean.setUploadUserId(diskFile.getUserid());
/*  61 */     fileBean.setFilemd5(diskFile.getFileMd5());
/*  62 */     fileBean.setFiletype(diskFile.getFileType());
/*     */     
/*  64 */     Query queryUserName = Query.query((CriteriaDefinition)Criteria.where("id").is(diskFile.getUserid()));
/*  65 */     UserDO user = (UserDO)this.mongoTemplate.findOne(queryUserName, UserDO.class);
/*  66 */     fileBean.setUploadUserName(user.getUsername());
/*     */     
/*  68 */     return fileBean;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<FileBean> findChildrenFiles(String userId, String folderId) {
/*  73 */     return findChildrenFiles(folderId);
/*     */   }
/*     */   public List<FileBean> findChildrenFiles(String folderId) {
/*  76 */     List<FileBean> children = new ArrayList<>();
/*     */ 
/*     */     
/*  79 */     Query query = Query.query((CriteriaDefinition)Criteria.where("pid").is(folderId));
/*  80 */     List<DiskFile> files = this.mongoTemplate.find(query, DiskFile.class);
/*     */     
/*  82 */     if (!files.isEmpty()) {
/*  83 */       for (DiskFile file : files) {
/*  84 */         children.add(diskFileToFileBean(file));
/*     */       }
/*     */     }
/*  87 */     return children;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getChunksByFilemd5(String fileMd5) {
/*  94 */     List<String> urls = new ArrayList<>();
/*     */ 
/*     */     
/*  97 */     Query query = Query.query((CriteriaDefinition)Criteria.where("filemd5").is(fileMd5));
/*  98 */     List<DiskMd5Chunk> chunks = this.mongoTemplate.find(query, DiskMd5Chunk.class);
/*     */     
/* 100 */     Collections.sort(chunks, new Comparator<DiskMd5Chunk>()
/*     */         {
/*     */           public int compare(DiskMd5Chunk chunk1, DiskMd5Chunk chunk2) {
/* 103 */             return chunk1.getChunkNumber().intValue() - chunk2.getChunkNumber().intValue();
/*     */           }
/*     */         });
/* 106 */     Collections.sort(chunks, Comparator.comparingInt(DiskMd5Chunk::getChunkNumber));
/*     */     
/* 108 */     for (DiskMd5Chunk chunk : chunks) {
/* 109 */       urls.add(chunk.getStorePath());
/*     */     }
/* 111 */     return urls;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getBytesByUrl(String url) throws IOException {
/* 116 */     FileInputStream input = new FileInputStream(url);
/* 117 */     ByteArrayOutputStream output = new ByteArrayOutputStream();
/*     */     
/* 119 */     IOUtils.copyLarge(input, output);
/* 120 */     return output.toByteArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveFile(DiskFile file) {
/* 127 */     this.fileListRepository.save(file);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void deleteFile(String filename) {
/* 133 */     Query query = Query.query((CriteriaDefinition)Criteria.where("filename").is(filename));
/*     */ 
/*     */ 
/*     */     
/* 137 */     this.mongoTemplate.remove(query, DiskFile.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DownloadBean getDownloadInfo(List<String> fileIds) {
/* 144 */     if (CollectionUtils.isEmpty(fileIds)) {
/* 145 */       throw new RuntimeException("请选择下载记录");
/*     */     }
/* 147 */     DownloadBean bean = new DownloadBean();
/* 148 */     bean.setFileNum(Integer.valueOf(0));
/* 149 */     bean.setFolderNum(Integer.valueOf(0));
/* 150 */     bean.setTotalSize(0L);
/*     */     
/* 152 */     for (String fileId : fileIds) {
/*     */       
/* 154 */       Query query = Query.query((CriteriaDefinition)Criteria.where("id").is(fileId));
/* 155 */       DiskFile file = (DiskFile)this.mongoTemplate.findOne(query, DiskFile.class);
/* 156 */       bean.setTotalSize(bean.getTotalSize() + file.getFileSize().longValue());
/* 157 */       if (file.getFileType().intValue() == 1) {
/*     */         
/* 159 */         bean.setFileNum(Integer.valueOf(bean.getFileNum().intValue() + 1));
/* 160 */       } else if (file.getFileType().intValue() == 0) {
/*     */         
/* 162 */         bean.setFolderNum(Integer.valueOf(bean.getFolderNum().intValue() + 1));
/*     */       } 
/* 164 */       dgGetDownloadInfo(file.getId(), bean);
/*     */     } 
/* 166 */     return bean;
/*     */   }
/*     */   
/*     */   private void dgGetDownloadInfo(String id, DownloadBean bean) {
/* 170 */     List<FileBean> childrenFiles = findChildrenFiles(id);
/*     */     
/* 172 */     if (!CollectionUtils.isEmpty(childrenFiles))
/* 173 */       for (FileBean file : childrenFiles) {
/* 174 */         bean.setTotalSize(bean.getTotalSize() + file.getFilesize());
/* 175 */         if (file.getFiletype().intValue() == 1) {
/*     */           
/* 177 */           bean.setFileNum(Integer.valueOf(bean.getFileNum().intValue() + 1));
/* 178 */         } else if (file.getFiletype().intValue() == 0) {
/*     */           
/* 180 */           bean.setFolderNum(Integer.valueOf(bean.getFolderNum().intValue() + 1));
/*     */         } 
/* 182 */         dgGetDownloadInfo(file.getId(), bean);
/*     */       }  
/*     */   }
/*     */ }


/* Location:              D:\workspace\IDEA\disk - 副本\target\classes\!\co\\ustc\download\service\impl\FileServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */