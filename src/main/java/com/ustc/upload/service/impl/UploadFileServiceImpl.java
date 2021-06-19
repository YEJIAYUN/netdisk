package com.ustc.upload.service.impl;

import com.google.common.collect.Interner;
import com.ustc.chain.core.HandlerInitializer;
import com.ustc.chain.core.Pipeline;
import com.ustc.chain.core.ResponsibleChain;
import com.ustc.chain.handler.ChunkRedisHandler;
import com.ustc.chain.handler.ChunkStoreHandler;
import com.ustc.chain.handler.ChunkValidateHandler;
import com.ustc.chain.param.ChunkRequest;
import com.ustc.chain.param.MergeRequest;
import com.ustc.entity.Chunk;
import com.ustc.entity.MergeFileBean;
import com.ustc.lock.Lock;
import com.ustc.upload.service.UploadFileService;
import com.ustc.utils.SpringContentUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 叶嘉耘
 */
@Component
@Transactional
public class UploadFileServiceImpl implements UploadFileService {
    @Autowired
    private SpringContentUtils scu;
    @Override
    public void uploadChunk(Chunk chunk) {
        // 1. 参数转换, 将chunk转换为责任链中的Request
        ChunkRequest chunkRequest = new ChunkRequest();
        BeanUtils.copyProperties(chunk, chunkRequest);
        // 2. 创建责任链的启动类
        ResponsibleChain responsibleChain = new ResponsibleChain();
        // 3. 组装责任链
        responsibleChain.loadHandler(new HandlerInitializer(chunkRequest, null) {
            @Override
            protected void initChannel(Pipeline line) {
                // 3.1 参数校验
                line.addLast(scu.getHandler(ChunkValidateHandler.class));
                // 3.2 校验是否支持对应格式
                // line.addLast(new ChunkFileSuffixHandler());
                // 3.3 切块存储
                line.addLast(scu.getHandler(ChunkStoreHandler.class));
                // 3.4 保存记录至Redis
                line.addLast(scu.getHandler(ChunkRedisHandler.class));
            }
        });
        // 4. 执行责任链
        responsibleChain.execute();
    }

    @Override
    public Integer checkFile(String fileMd5) {
        return null;
    }


    /**
     * 如果是文件上传, 锁的名字为 filemd5
     * 如果是文件夹上传, 则锁的名字为 userid-folderid-rootname
     *
     * @param bean
     */
    @Override
    public void mergeChunk(MergeFileBean bean) {
        // 1. 获取文件的md5, 将其当作锁
        String lockname = bean.getFilemd5();
        // 2. 确保相同的字符串指向常量池中的同一个对象
        Interner<String> lock = Lock.getStringPool();
        // 3. 如果是文件夹

        synchronized (lock.intern(lockname)) {
            MergeRequest mergeRequest = new MergeRequest();

            ResponsibleChain chain = new ResponsibleChain();
            chain.loadHandler(new HandlerInitializer(mergeRequest, null) {
                @Override
                protected void initChannel(Pipeline line) {
                    //1.基本参数的校验
                    //2.检验容量是否足够
                    //3.从Redis获取切块记录
                    //4.校验文件是否完整
                    //5.校验md5是否已经在disk_md5表存在
                    //6.保存disk_md5表
                    //7.保存disk_chunk表
                    //8.如果是文件夹上传，则先创建文件夹
                    //9.保存disk_file表
                    //10.如果是图片则裁剪；如果是视频则截图
                    //11.如果是相册上传图片，则关联相册
                    //12.更新容量、推送容量
                    //13.新增Solr
                    //14.删除Redis记录
                }
            });

            chain.execute();
        }


    }
}
