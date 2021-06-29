package com.ustc.download.controller;

import com.ustc.download.service.FileService;
import com.ustc.entity.DiskFile;
import java.util.List;

import com.ustc.entity.FileListBean;
import com.ustc.entity.PageInfo;
import com.ustc.entity.SessionUserBean;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class FileListController
{
    @Autowired
    private FileService fileService;

//    @RequestMapping({"/filelist"})
//    public String ListAllFile(Model model) {
//    List<DiskFile> fileList = this.fileService.findAllFile();
//    model.addAttribute("filelist", fileList);
//    return "filelist";
//    }

    @ApiOperation(value="全部-列表",notes="全部-列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页数（从1开始）",dataType = "Integer",paramType="query",required=true),
            @ApiImplicitParam(name = "limit", value = "分页记录数",dataType = "Integer",paramType="query",required=true),
            @ApiImplicitParam(name = "pid", value = "父节点ID（默认是0）",dataType = "String",paramType="query",required=true),
            @ApiImplicitParam(name = "orderfield", value = "排序字段",dataType = "String",paramType="query",required=false),
            @ApiImplicitParam(name = "ordertype", value = "排序类型",dataType = "String",paramType="query",required=false),
            @ApiImplicitParam(name = "token", value = "token",dataType = "String",paramType="query",required=true)
    })
    @PostMapping("/findList")
    public PageInfo<FileListBean> findList(PageInfo<FileListBean> pi, String pid, String orderfield, String ordertype, HttpServletRequest request){
        SessionUserBean loginUser = (SessionUserBean)request.getSession().getAttribute("loginUser");

        return fileService.findPageList();
    }
}
