package com.ustc.download.service;

import com.ustc.entity.*;

import java.io.IOException;
import java.util.List;

public interface FileService {
  // 文件列表的分页
  PageInfo<FileListBean> findPageList();

  List<DiskFile> findAllFile();
  
  FileBean findOne(String paramString);
  
  List<FileBean> findChildrenFiles(String paramString1, String paramString2);
  
  List<FileBean> findChildrenFiles(String paramString);
  
  List<String> getChunksByFilemd5(String paramString);
  
  byte[] getBytesByUrl(String paramString) throws IOException;
  
  void saveFile(DiskFile paramDiskFile);
  
  DownloadBean getDownloadInfo(List<String> paramList);
}