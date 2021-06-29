package com.ustc.upload.controller;

import com.ustc.entity.FileSearchBean;
import com.ustc.entity.PageInfo;
import com.ustc.upload.service.FileSearchService;
import com.ustc.utils.CommonResult;
import com.ustc.utils.CommonResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 叶嘉耘
 * @date 2021/6/27
 */
@Api(tags = "文件搜索及列表")
@RestController
@RequestMapping("disk/fileSearch")
public class FileSearchController {

    @Autowired
    private FileSearchService fileSearchService;

    @ApiOperation("搜索")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前分页数", dataTypeClass = Integer.class, required = true),
            @ApiImplicitParam(name = "limit", value = "每页记录数", dataTypeClass = Integer.class, required = true),
            @ApiImplicitParam(name = "filename", value = "文件名", dataTypeClass = String.class, required = true),
    })
    @PostMapping("/search")
    public PageInfo<FileSearchBean> search(PageInfo<FileSearchBean> pageInfo, String filename, HttpServletRequest request) throws Exception {
        return fileSearchService.search(filename, "test", pageInfo.getPage(), pageInfo.getLimit());
    }

}
