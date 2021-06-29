/*     */ package com.ustc.download.controller;
/*     */ 
/*     */ import com.ustc.download.service.FileService;
/*     */ import com.ustc.entity.DownloadBean;
/*     */ import com.ustc.entity.FileBean;
/*     */ import com.ustc.entity.SessionUserBean;
/*     */ import com.ustc.utils.CapacityUtils;
/*     */ import com.ustc.utils.CommonResult;
/*     */ import com.ustc.utils.CommonResultUtils;
/*     */ import com.ustc.utils.FileZipUtils;
/*     */ import com.ustc.utils.ValidateUtils;
/*     */ import io.swagger.annotations.Api;
/*     */ import io.swagger.annotations.ApiImplicitParam;
/*     */ import io.swagger.annotations.ApiImplicitParams;
/*     */ import io.swagger.annotations.ApiOperation;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.URLEncoder;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.servlet.ServletOutputStream;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.beans.factory.annotation.Value;
/*     */ import org.springframework.util.CollectionUtils;
/*     */ import org.springframework.web.bind.annotation.GetMapping;
/*     */ import org.springframework.web.bind.annotation.PostMapping;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ import org.springframework.web.bind.annotation.RestController;
/*     */ 
/*     */ 
/*     */ 
/*     */ @Api(tags = {"文件下载"})
/*     */ @RestController
/*     */ @RequestMapping({"/disk/filedownload"})
/*     */ public class FileDownloadController
/*     */ {
/*     */   @Autowired
/*     */   private FileService fileService;
/*     */   @Value("${store.store-path")
/*     */   private String rootPath;
/*     */   
/*     */   @ApiOperation(value = "单文件下载", notes = "单文件下载")
/*     */   @ApiImplicitParams({@ApiImplicitParam(name = "fileid", value = "文件ID", dataType = "String", paramType = "query", required = true), @ApiImplicitParam(name = "token", value = "token", dataType = "String", paramType = "query", required = true)})
/*     */   @GetMapping({"/download"})
/*     */   public void download(String fileid, HttpServletRequest request, HttpServletResponse response) {
/*     */     try {
/*  52 */       FileBean fb = this.fileService.findOne(fileid);
/*     */ 
/*     */ 
/*     */       
/*  56 */       String filemd5 = fb.getFilemd5();
/*  57 */       String filename = fb.getFilename();
/*  58 */       SessionUserBean loginUser = (SessionUserBean)request.getSession().getAttribute("loginUser");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  65 */       if (!loginUser.getUserId().equals(fb.getUploadUserId()))
/*     */       {
/*  67 */         System.out.println("您不是文件的拥有者");
/*     */       }
/*     */ 
/*     */       
/*  71 */       String userAgent = request.getHeader("User-Agent");
/*  72 */       if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
/*  73 */         filename = URLEncoder.encode(filename, "UTF-8");
/*     */       } else {
/*  75 */         filename = new String(filename.getBytes("UTF-8"), "ISO-8859-1");
/*     */       } 
/*  77 */       response.setHeader("content-disposition", "attachment;filename=" + filename);
/*     */ 
/*     */ 
/*     */       
/*  81 */       List<String> urls = this.fileService.getChunksByFilemd5(filemd5);
/*     */ 
/*     */ 
/*     */       
/*  85 */       ServletOutputStream servletOutputStream = response.getOutputStream();
/*  86 */       for (String url : urls) {
/*  87 */         byte[] bytes = this.fileService.getBytesByUrl(url);
/*  88 */         servletOutputStream.write(bytes);
/*  89 */         servletOutputStream.flush();
/*     */       } 
/*  91 */       servletOutputStream.close();
/*  92 */     } catch (UnsupportedEncodingException e) {
/*  93 */       e.printStackTrace();
/*  94 */     } catch (IOException e) {
/*  95 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ApiOperation(value = "多文件下载-获取文件信息", notes = "是否大于200M（超出200M则使用客户端下载）")
/*     */   @ApiImplicitParams({@ApiImplicitParam(name = "idjson", value = "勾选文件ID（[{'id':'xx'}]）", dataType = "String", paramType = "query", required = true), @ApiImplicitParam(name = "token", value = "token", dataType = "String", paramType = "query", required = true)})
/*     */   @PostMapping({"/getDownloadInfo"})
/*     */   public CommonResult getDownloadInfo(String idjson, HttpServletRequest request) {
/*     */     try {
/* 107 */       ValidateUtils.validate(idjson, "下载记录");
/*     */       
/* 109 */       List<String> fileids = new ArrayList<>();
/* 110 */       String[] ids = idjson.split(",");
/* 111 */       for (String id : ids) {
/* 112 */         fileids.add(id);
/*     */       }
/* 114 */       SessionUserBean loginUser = (SessionUserBean)request.getSession().getAttribute("loginUser");
/* 115 */       DownloadBean bean = this.fileService.getDownloadInfo(fileids);
/* 116 */       bean.setTotalSizeName(CapacityUtils.convert(Long.valueOf(bean.getTotalSize())));
/* 117 */       if (bean.getTotalSize() <= 209715200L) {
/* 118 */         bean.setIsBig(Integer.valueOf(0));
/*     */       } else {
/* 120 */         bean.setIsBig(Integer.valueOf(1));
/*     */       } 
/* 122 */       return CommonResultUtils.success(bean);
/* 123 */     } catch (Exception e) {
/* 124 */       return CommonResultUtils.error(Integer.valueOf(0), e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @PostMapping({"/mergeFiles"})
/*     */   public CommonResult mergeFiles(String downloadName, String downloadSuffix, String idjson, HttpServletRequest request, HttpServletResponse response) {
/*     */     try {
/* 136 */       ValidateUtils.validate(downloadName, "下载文件名称");
/* 137 */       ValidateUtils.validate(downloadSuffix, "下载文件格式");
/* 138 */       ValidateUtils.validate(idjson, "下载记录");
/* 139 */       String[] fileids = idjson.split(",");
/*     */ 
/*     */       
/* 142 */       SessionUserBean loginUser = (SessionUserBean)request.getSession().getAttribute("loginUser");
/* 143 */       String path = this.rootPath + "/" + loginUser.getUserId() + "/" + downloadName;
/*     */ 
/*     */       
/* 146 */       File fileRootZip = new File(path + "." + downloadSuffix);
/* 147 */       if (fileRootZip.exists()) {
/* 148 */         throw new RuntimeException("该下载名称已经存在,请更换一个!");
/*     */       }
/*     */       
/* 151 */       File fileRoot = new File(path);
/* 152 */       if (!fileRoot.exists()) {
/* 153 */         fileRoot.mkdirs();
/*     */       } else {
/* 155 */         throw new RuntimeException("该下载名称已经存在,请更换一个!");
/*     */       } 
/*     */       
/* 158 */       for (String fileid : fileids) {
/* 159 */         FileBean bean = this.fileService.findOne(fileid);
/* 160 */         if (bean.getFiletype().intValue() == 0) {
/* 161 */           File file = new File(path + "/" + bean.getFilename());
/*     */           
/* 163 */           if (!file.exists()) {
/* 164 */             file.mkdirs();
/*     */           }
/*     */           
/* 167 */           dgDownload(loginUser.getUserId(), path + "/" + bean.getFilename(), bean.getId());
/*     */         } else {
/* 169 */           String filename = path + "/" + bean.getFilename();
/* 170 */           FileOutputStream out = new FileOutputStream(filename);
/*     */ 
/*     */           
/* 173 */           List<String> urls = this.fileService.getChunksByFilemd5(bean.getFilemd5());
/* 174 */           for (String url : urls) {
/*     */             
/* 176 */             byte[] bytes = this.fileService.getBytesByUrl(url);
/*     */ 
/*     */             
/* 179 */             out.write(bytes);
/* 180 */             out.flush();
/*     */           } 
/* 182 */           out.close();
/*     */         } 
/*     */       } 
/*     */       
/* 186 */       String zipPath = path + "." + downloadSuffix;
/* 187 */       FileZipUtils.fileToZip(path, zipPath);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 198 */       return CommonResultUtils.success("压缩成功");
/* 199 */     } catch (Exception e) {
/* 200 */       e.printStackTrace();
/*     */ 
/*     */       
/* 203 */       return CommonResultUtils.error(Integer.valueOf(1), "e.getMessage()");
/*     */     } 
/*     */   }
/*     */   private void dgDownload(String userid, String path, String pid) throws Exception {
/* 207 */     List<FileBean> beans = this.fileService.findChildrenFiles(userid, pid);
/* 208 */     if (!CollectionUtils.isEmpty(beans)) {
/* 209 */       for (FileBean bean : beans) {
/* 210 */         if (bean.getFiletype().intValue() == 0) {
/* 211 */           File file = new File(path + "/" + bean.getFilename());
/*     */           
/* 213 */           if (!file.exists()) {
/* 214 */             file.mkdirs();
/*     */           }
/*     */           
/* 217 */           dgDownload(userid, path + "/" + bean.getFilename(), bean.getId()); continue;
/*     */         } 
/* 219 */         String filename = path + "/" + bean.getFilename();
/* 220 */         FileOutputStream out = new FileOutputStream(filename);
/* 221 */         List<String> urls = this.fileService.getChunksByFilemd5(bean.getFilemd5());
/* 222 */         for (String url : urls) {
/* 223 */           byte[] bytes = this.fileService.getBytesByUrl(url);
/* 224 */           out.write(bytes);
/* 225 */           out.flush();
/*     */         } 
/* 227 */         out.close();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ApiOperation(value = "多文件下载-压缩文件下载", notes = "多文件下载-压缩文件下载")
/*     */   @ApiImplicitParams({@ApiImplicitParam(name = "filename", value = "压缩后的名称", dataType = "String", paramType = "query", required = true), @ApiImplicitParam(name = "path", value = "压缩文件存储路径", dataType = "String", paramType = "query", required = true), @ApiImplicitParam(name = "token", value = "token", dataType = "String", paramType = "query", required = true)})
/*     */   @GetMapping({"/downloadZip"})
/*     */   public void downloadZip(String filename, String path, HttpServletRequest request, HttpServletResponse response) {
/*     */     try {
/* 242 */       String userAgent = request.getHeader("User-Agent");
/* 243 */       if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
/* 244 */         filename = URLEncoder.encode(filename, "UTF-8");
/*     */       } else {
/* 246 */         filename = new String(filename.getBytes("UTF-8"), "ISO-8859-1");
/*     */       } 
/* 248 */       response.setHeader("content-disposition", "attachment;filename=" + filename);
/*     */       
/* 250 */       ServletOutputStream servletOutputStream = response.getOutputStream();
/*     */       
/* 252 */       File file = new File(path);
/* 253 */       FileInputStream input = new FileInputStream(file);
/* 254 */       byte[] bs = new byte[1024];
/*     */       int len;
/* 256 */       while ((len = input.read(bs)) != -1) {
/* 257 */         servletOutputStream.write(bs, 0, len);
/* 258 */         servletOutputStream.flush();
/*     */       } 
/* 260 */       input.close();
/* 261 */       servletOutputStream.close();
/*     */ 
/*     */       
/* 264 */       file.delete();
/*     */     }
/* 266 */     catch (Exception e) {
/* 267 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\workspace\IDEA\disk - 副本\target\classes\!\co\\ustc\download\controller\FileDownloadController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */