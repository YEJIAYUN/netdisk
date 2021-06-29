/*     */ package com.ustc.entity;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FileBean
/*     */   implements Serializable
/*     */ {
/*     */   private String id;
/*     */   private String pid;
/*     */   private String pname;
/*     */   private String filename;
/*     */   private Date uploadDate;
/*     */   private String fileSuffix;
/*     */   private long filesize;
/*     */   private String uploadUserId;
/*     */   private String filemd5;
/*     */   private Integer filetype;
/*     */   private String uploadUserName;
/*     */   
/*     */   public String getId() {
/*  27 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/*  31 */     this.id = id;
/*     */   }
/*     */   
/*     */   public String getPid() {
/*  35 */     return this.pid;
/*     */   }
/*     */   
/*     */   public void setPid(String pid) {
/*  39 */     this.pid = pid;
/*     */   }
/*     */   
/*     */   public String getPname() {
/*  43 */     return this.pname;
/*     */   }
/*     */   
/*     */   public void setPname(String pname) {
/*  47 */     this.pname = pname;
/*     */   }
/*     */   
/*     */   public String getFilename() {
/*  51 */     return this.filename;
/*     */   }
/*     */   
/*     */   public void setFilename(String filename) {
/*  55 */     this.filename = filename;
/*     */   }
/*     */   
/*     */   public Date getUploadDate() {
/*  59 */     return this.uploadDate;
/*     */   }
/*     */   
/*     */   public void setUploadDate(Date uploadDate) {
/*  63 */     this.uploadDate = uploadDate;
/*     */   }
/*     */   
/*     */   public String getFileSuffix() {
/*  67 */     return this.fileSuffix;
/*     */   }
/*     */   
/*     */   public void setFileSuffix(String fileSuffix) {
/*  71 */     this.fileSuffix = fileSuffix;
/*     */   }
/*     */   
/*     */   public long getFilesize() {
/*  75 */     return this.filesize;
/*     */   }
/*     */   
/*     */   public void setFilesize(long filesize) {
/*  79 */     this.filesize = filesize;
/*     */   }
/*     */   
/*     */   public String getUploadUserName() {
/*  83 */     return this.uploadUserName;
/*     */   }
/*     */   
/*     */   public void setUploadUserName(String uploadUserName) {
/*  87 */     this.uploadUserName = uploadUserName;
/*     */   }
/*     */   
/*     */   public String getFilemd5() {
/*  91 */     return this.filemd5;
/*     */   }
/*     */   
/*     */   public void setFilemd5(String filemd5) {
/*  95 */     this.filemd5 = filemd5;
/*     */   }
/*     */   
/*     */   public Integer getFiletype() {
/*  99 */     return this.filetype;
/*     */   }
/*     */   
/*     */   public void setFiletype(Integer filetype) {
/* 103 */     this.filetype = filetype;
/*     */   }
/*     */   
/*     */   public String getUploadUserId() {
/* 107 */     return this.uploadUserId;
/*     */   }
/*     */   
/*     */   public void setUploadUserId(String uploadUserId) {
/* 111 */     this.uploadUserId = uploadUserId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 119 */     return "FileBean{id='" + this.id + '\'' + ", pid='" + this.pid + '\'' + ", pname='" + this.pname + '\'' + ", filename='" + this.filename + '\'' + ", uploadDate=" + this.uploadDate + ", fileSuffix='" + this.fileSuffix + '\'' + ", filesize=" + this.filesize + ", uploadUserId='" + this.uploadUserId + '\'' + ", filemd5='" + this.filemd5 + '\'' + ", filetype=" + this.filetype + ", uploadUserName='" + this.uploadUserName + '\'' + '}';
/*     */   }
/*     */ }


/* Location:              D:\workspace\IDEA\disk - 副本\target\classes\!\co\\ustc\entity\FileBean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */