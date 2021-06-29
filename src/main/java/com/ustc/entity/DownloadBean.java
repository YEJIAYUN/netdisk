/*    */ package com.ustc.entity;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DownloadBean
/*    */   implements Serializable
/*    */ {
/*    */   private Integer fileNum;
/*    */   private Integer folderNum;
/*    */   private long totalSize;
/*    */   private String totalSizeName;
/*    */   private Integer isBig;
/*    */   
/*    */   public DownloadBean() {}
/*    */   
/*    */   public DownloadBean(Integer fileNum, Integer folderNum, long totalSize, String totalSizeName, Integer isBig) {
/* 29 */     this.fileNum = fileNum;
/* 30 */     this.folderNum = folderNum;
/* 31 */     this.totalSize = totalSize;
/* 32 */     this.totalSizeName = totalSizeName;
/* 33 */     this.isBig = isBig;
/*    */   }
/*    */ 
/*    */   
/*    */   public Integer getFileNum() {
/* 38 */     return this.fileNum;
/*    */   }
/*    */   
/*    */   public void setFileNum(Integer fileNum) {
/* 42 */     this.fileNum = fileNum;
/*    */   }
/*    */   
/*    */   public Integer getFolderNum() {
/* 46 */     return this.folderNum;
/*    */   }
/*    */   
/*    */   public void setFolderNum(Integer folderNum) {
/* 50 */     this.folderNum = folderNum;
/*    */   }
/*    */   
/*    */   public long getTotalSize() {
/* 54 */     return this.totalSize;
/*    */   }
/*    */   
/*    */   public void setTotalSize(long totalSize) {
/* 58 */     this.totalSize = totalSize;
/*    */   }
/*    */   
/*    */   public String getTotalSizeName() {
/* 62 */     return this.totalSizeName;
/*    */   }
/*    */   
/*    */   public void setTotalSizeName(String totalSizeName) {
/* 66 */     this.totalSizeName = totalSizeName;
/*    */   }
/*    */   
/*    */   public Integer getIsBig() {
/* 70 */     return this.isBig;
/*    */   }
/*    */   
/*    */   public void setIsBig(Integer isBig) {
/* 74 */     this.isBig = isBig;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 81 */     return "DownloadBean{fileNum=" + this.fileNum + ", folderNum=" + this.folderNum + ", totalSize=" + this.totalSize + ", totalSizeName='" + this.totalSizeName + '\'' + ", isBig=" + this.isBig + '}';
/*    */   }
/*    */ }


/* Location:              D:\workspace\IDEA\disk - 副本\target\classes\!\co\\ustc\entity\DownloadBean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */