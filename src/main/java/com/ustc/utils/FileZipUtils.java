/*    */ package com.ustc.utils;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.FileOutputStream;
/*    */ import java.io.InputStream;
/*    */ import java.util.zip.ZipEntry;
/*    */ import java.util.zip.ZipOutputStream;
/*    */ 
/*    */ public class FileZipUtils {
/*    */   public static void fileToZip(String sourceFilePath, String zipPath) {
/*    */     try {
/* 13 */       File zipFile = new File(zipPath);
/* 14 */       ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
/* 15 */       recursionZip(sourceFilePath, "", zipOut);
/* 16 */       zipOut.close();
/* 17 */       delFile(sourceFilePath);
/* 18 */     } catch (Exception e) {
/* 19 */       throw new RuntimeException(e.getMessage());
/*    */     } 
/*    */   }
/*    */   
/*    */   private static void delFile(String path) {
/* 24 */     File file = new File(path);
/* 25 */     File[] files = file.listFiles();
/* 26 */     for (int i = 0; i < files.length; i++) {
/* 27 */       File f = files[i];
/* 28 */       if (f.isFile()) {
/* 29 */         f.delete();
/*    */       } else {
/* 31 */         dgDel(f);
/*    */       } 
/*    */     } 
/* 34 */     file.delete();
/*    */   }
/*    */   private static void dgDel(File file) {
/* 37 */     File[] files = file.listFiles();
/* 38 */     for (int i = 0; i < files.length; i++) {
/* 39 */       File f = files[i];
/* 40 */       if (f.isFile()) {
/* 41 */         f.delete();
/*    */       } else {
/* 43 */         dgDel(f);
/*    */       } 
/*    */     } 
/* 46 */     file.delete();
/*    */   }
/*    */ 
/*    */   
/*    */   public static void recursionZip(String filePath, String baseDir, ZipOutputStream zipOut) throws Exception {
/* 51 */     File srcFile = new File(filePath);
/* 52 */     File[] files = srcFile.listFiles();
/* 53 */     for (File file : files) {
/* 54 */       if (file.isFile()) {
/* 55 */         zipFile(file, baseDir, zipOut);
/*    */       } else {
/* 57 */         recursionZip(filePath + File.separator + file.getName(), baseDir + file.getName() + File.separator, zipOut);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void zipFile(File file, String baseDir, ZipOutputStream zipOut) throws Exception {
/* 63 */     InputStream input = new FileInputStream(file);
/*    */     
/* 65 */     zipOut.putNextEntry(new ZipEntry(baseDir + file.getName()));
/* 66 */     byte[] buffer = new byte[4096];
/* 67 */     int readByte = -1;
/* 68 */     while ((readByte = input.read(buffer)) != -1) {
/* 69 */       zipOut.write(buffer, 0, readByte);
/*    */     }
/* 71 */     input.close();
/* 72 */     zipOut.closeEntry();
/*    */   }
/*    */ }


/* Location:              D:\workspace\IDEA\disk - 副本\target\classes\!\co\\ust\\utils\FileZipUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */