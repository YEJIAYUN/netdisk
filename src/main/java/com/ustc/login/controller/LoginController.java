package com.ustc.login.controller;

/**
 * 用户名和密码验证
 * URL = /user/login
 * 登录成功，则进入导航页dashboard.html
 * 登录失败，重新回到登录页index.html
 */

import com.ustc.utils.CommonResult;
import com.ustc.utils.CommonResultUtils;
import com.ustc.entity.SessionUserBean;
import com.ustc.login.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Api(tags="登录及退出")
@RestController
@RequestMapping("/security")
//@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @ApiOperation(value="登录",notes="登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "账号",dataType = "String",paramType="query",required=true),
            @ApiImplicitParam(name = "password", value = "密码",dataType = "String",paramType="query",required=true),
    })
    @PostMapping("/login")
    public String login(String username, String password,
                        Model model, HttpSession httpSession){

        String CorrectPasswd = userService.getPasswordByUsername(username);  // 正确的密码

        if(!StringUtils.isEmpty(username) && CorrectPasswd.equals(password)){
            // 登录成功
            // 在httpSession中加入SessionUserBean
            SessionUserBean sessionUserBean = new SessionUserBean(username);
            httpSession.setAttribute("loginUser", sessionUserBean);

            return "redirect:/main.html";
        } else {
            // 登录失败
            model.addAttribute("msg","用户名或密码错误！");
            return "index";
        }
    }

//    @ApiOperation(value="登录",notes="登录")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "username", value = "账号",dataType = "String",paramType="query",required=true),
//            @ApiImplicitParam(name = "password", value = "密码",dataType = "String",paramType="query",required=true),
//    })
//    @PostMapping("/login")
//    public Result login(String username,String password,HttpServletRequest request){
//        try{
//            //登录
//            SessionUserBean bean=userService.login(username, password);
//            if(bean!=null){
//                //设置用户信息
//                request.setAttribute("userid",bean.getId());
//                request.setAttribute("username",bean.getNickname());
//
//                //初始化容量
//                userCapacityService.init(bean.getId(), bean.getNickname());
//            }else{
//                throw new RuntimeException("用户名或密码错误");
//            }
//            return ResultUtils.success("认证成功", bean);
//        }catch(Exception e){
//            return ResultUtils.error(e.getMessage());
//        }
//    }

    @ApiOperation(value="登出",notes="登出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token",dataType = "String",paramType="query",required=true)
    })
    @PostMapping("/logout")
    public String logout(String token,HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/index.html";
    }

//    @ApiOperation(value="登出",notes="登出")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "token", value = "token",dataType = "String",paramType="query",required=true)
//    })
//    @PostMapping("/logout")
//    public Result logout(String token,HttpServletRequest request){
//        try{
//            SessionUserBean ui=userService.getUserByToken(token);
//            if(ui!=null){
//                //设置用户信息
//                request.setAttribute("userid",ui.getId());
//                request.setAttribute("username",ui.getNickname());
//
//                //清空token
//                userService.logout(token);
//            }
//            return ResultUtils.success("退出成功", null);
//        }catch(Exception e){
//            return ResultUtils.success("退出成功", null);
//        }
//    }
}