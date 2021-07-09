package com.ustc.filecommon.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ustc.entity.FileListBean;
import com.ustc.exception.ServiceException;
import com.ustc.filecommon.service.FileCommonService;
import com.ustc.utils.CommonResult;
import com.ustc.utils.CommonResultUtils;
import com.ustc.utils.FileUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * @author 叶嘉耘
 * @date 2021/7/8
 */

@Api(tags = "文件通用操作")
@RestController
@RequestMapping("disk/filecommon")
public class FileCommonController {

    @Autowired
    FileCommonService fileCommonService;

    @ApiOperation("查找文件夹")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "pid", value = "父文件夹id", dataTypeClass = String.class, required = true),
        @ApiImplicitParam(name = "idjson", value = "id的Json序列化格式, 含有id和文件名", dataTypeClass = String.class, required = true),
        @ApiImplicitParam(name = "token", dataTypeClass = String.class)
    })
    @PostMapping("/findFolderList")
    public CommonResult<List<FileListBean>> findFolderList(@RequestParam("pid") String pid,
                                                           @RequestParam("idjson") String idJson,
                                                           String token) throws JsonProcessingException {
        // 将idJson中的所有id存入列表中
        List<String> idList = FileUtils.idJsonToList(idJson);
        // 根据token获取用户id
        // 查询结果
        return CommonResultUtils.success(fileCommonService.findFolderList("test", pid, idList));
    }

    @ApiOperation("移动文件到指定文件夹")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "pid", value = "目的父文件夹id", dataTypeClass = String.class, required = true),
        @ApiImplicitParam(name = "idjson", value = "id的Json序列化格式, 含有id和文件名", dataTypeClass = String.class, required = true),
        @ApiImplicitParam(name = "token", dataTypeClass = String.class)
    })
    @PostMapping("/moveTo")
    public CommonResult<String> move(@RequestParam("toid") String pid, @RequestParam("idjson") String idJson,
                                     String token) throws IOException {
        List<String> idList = FileUtils.idJsonToList(idJson);
        // 根据token获取用户id
        fileCommonService.move("test", pid, idList);
        return CommonResultUtils.success("移动成功");
    }

    @ApiOperation("文件重命名")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "文件id", dataTypeClass = String.class, required = true),
        @ApiImplicitParam(name = "newName", value = "文件id", dataTypeClass = String.class, required = true),
        @ApiImplicitParam(name = "token", dataTypeClass = String.class)
    })
    @PostMapping("/rename")
    public CommonResult<String> rename(@RequestParam("id") String id, @RequestParam("filename") String newName,
                                       String token) throws IOException {
        // 根据token获取用户id
        fileCommonService.rename("test", id, newName);
        return CommonResultUtils.success("重命名成功");
    }

    @PostMapping("/findOne")
    public CommonResult<FileListBean> findOne(@RequestParam("id") String id, String token) {
        return CommonResultUtils.success(fileCommonService.findOneRecord("test", id));
    }
}
