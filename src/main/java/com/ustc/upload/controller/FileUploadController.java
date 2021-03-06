package com.ustc.upload.controller;

import com.ustc.entity.Chunk;
import com.ustc.entity.ChunkPojo;
import com.ustc.entity.MergeFileBean;
import com.ustc.exception.ServiceException;
import com.ustc.exception.ServiceExceptionEnum;
import com.ustc.upload.service.UploadFileService;
import com.ustc.utils.CommonResult;
import com.ustc.utils.CommonResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 叶嘉耘
 */
@Api(tags = "文件上传")
@RestController
@RequestMapping("/disk/fileupload")
public class FileUploadController {

    @Autowired
    private UploadFileService uploadFileService;

    /**
     * MultipartFile类用于以表单的形式进行文件上传功能
     */
    @ApiOperation("切块上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件切块", dataTypeClass = MultipartFile.class, paramType = "form", required = true),
            @ApiImplicitParam(name = "token", dataTypeClass = String.class, required = true)
    })
    @PostMapping("/uploadChunk")
    public CommonResult<String> uploadChunk(MultipartFile file, ChunkPojo chunkPojo, String token) throws IOException, SolrServerException {
        // 1. 通过token获取用户信息
        System.out.println("uploadChunk中收到的uuid: " + chunkPojo.getUuid());
        // 2. 判断切块是否为空
        if (file == null) {
            throw new ServiceException(ServiceExceptionEnum.CHUNK_NOT_NULL);
        }
        // 3. 参数设置
        Chunk chunk = new Chunk();
        BeanUtils.copyProperties(chunkPojo, chunk);
        chunk.setBytes(file.getBytes());
        // 设置用户id、用户名
        chunk.setUserid("test");
        chunk.setUsername("test");

        // 4. 调用切块上传接口
        uploadFileService.uploadChunk(chunk);

        return CommonResultUtils.success("切块上传成功");
    }

    @ApiOperation("检查文件是否存在")
    @ApiImplicitParam(name = "filemd5", value = "文件md5", dataTypeClass = String.class, required = true)
    @PostMapping("/checkFile")
    public CommonResult<Integer> checkFile(String filemd5) {
        return CommonResultUtils.success(uploadFileService.checkFile(filemd5));
    }

    @ApiOperation("合并切块")
    @PostMapping("/mergeChunk")
    public CommonResult<String> mergeChunk(MergeFileBean bean) throws ServiceException, SolrServerException, IOException {
        System.out.println("进入合并切块");
        // 获取用户信息
        bean.setUserid("test");
        bean.setUsername("test");
        System.out.println("mergeChunk中收到的uuid: " + bean.getUuid());
        // 合并切块
        uploadFileService.mergeChunk(bean);
        return CommonResultUtils.success("切块合并成功");
    }
}
