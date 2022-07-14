package com.imooc.controller;

import com.imooc.pojo.Users;
import com.imooc.pojo.bo.UserBO;
import com.imooc.service.UserService;
import com.imooc.utils.CookieUtils;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.JsonUtils;
import com.imooc.utils.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.HttpMethod;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.crypto.interfaces.PBEKey;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: 通行证
 * @author: <a href="mailto:guang.chen@zkteco.com">guang.chen</a>
 * @create: 2022-06-27
 */
@RestController
@RequestMapping("passport")
@Api(value = "注册登录",tags = "用于注册登录的Controller")
public class PassportController {
    @Autowired
    private UserService userService;

    @GetMapping("/usernameIsExist")
    @ApiOperation(value = "用户名是否存在",notes = "用户名是否存在",httpMethod = "GET")
    public IMOOCJSONResult usernameIsExist(@RequestParam String username){
        //1. 判断用户名不能为空
        if (StringUtils.isBlank(username)){
            return IMOOCJSONResult.errorMsg("用户名不能为空");
        }
        //2.查找注册的用户名是否存在
        boolean isExist = userService.QueryUsernameIsExit(username);
        if (isExist){
            return IMOOCJSONResult.errorMsg("用户名已经存在");
        }

        //3 请求成功，用户名没有重复
        return IMOOCJSONResult.ok();
    }

    @PostMapping("regist")
    @ApiOperation(value = "用户注册",notes = "用户注册",httpMethod = "POST")
    public IMOOCJSONResult regist(@RequestBody UserBO userBO,HttpServletRequest request,HttpServletResponse response){
        String username = userBO.getUsername();
        String password = userBO.getPassword();
        String confirmPwd = userBO.getConfirmPassword();
        //0.判断用户名和密码不能为空
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password) || StringUtils.isBlank(confirmPwd)){
            return IMOOCJSONResult.errorMsg("用户名和密码不能为空");
        }
        //1.判断用户名是否重复
        boolean isExist = userService.QueryUsernameIsExit(username);
        if (isExist){
            return IMOOCJSONResult.errorMsg("用户名已经存在");
        }
        //2.判断密码长度
        if (password.length()<6){
            return IMOOCJSONResult.errorMsg("密码长度不能少于6");
        }
        //3.判断两次密码是否相同
        if (!password.equals(confirmPwd)){
            return IMOOCJSONResult.errorMsg("两次密码输入不一致");
        }

        Users userResult = userService.createUser(userBO);
        setNullProperty(userResult);

        CookieUtils.setCookie(request,response,"user", JsonUtils.objectToJson(userResult),true);

        //TODO 生成用户token，存入redis会话
        //TODO 同步购物车数据

        return IMOOCJSONResult.ok();
    }

    @PostMapping("login")
    @ApiOperation(value = "用户登录",notes = "用户登录",httpMethod = "POST")
    public IMOOCJSONResult login(@RequestBody UserBO userBO, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String username = userBO.getUsername();
        String password = userBO.getPassword();
        //0.判断用户名和密码不能为空
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password) ){
            return IMOOCJSONResult.errorMsg("用户名和密码不能为空");
        }
        Users userResult = userService.queryUserForLogin(username, MD5Utils.getMD5Str(password));

        if (userResult==null){
            return IMOOCJSONResult.errorMsg("用户名或者密码不正确");
        }
        setNullProperty(userResult);

        CookieUtils.setCookie(request,response,"user", JsonUtils.objectToJson(userResult),true);

        //TODO 生成用户token，存入redis会话
        //TODO 同步购物车数据

        return IMOOCJSONResult.ok(userResult);
    }
    /*
     * @Description  将用户信息隐藏
     * @author <a href="mailto:guang.chen@zkteco.com">guang.chen</a>
     * @since v1.0
     * @Date  2022/6/30 22:15
     * @Param [userResult]
     * @return com.imooc.pojo.Users
     **/
    private Users setNullProperty(Users userResult) {
        userResult.setPassword(null);
        userResult.setMobile(null);
        userResult.setEmail(null);
        userResult.setCreatedTime(null);
        userResult.setUpdatedTime(null);
        userResult.setBirthday(null);
        return userResult;
    }

    @ApiOperation(value = "用户退出登录",notes = "用户退出登录",httpMethod = "POST")
    @PostMapping("/logout")
    public IMOOCJSONResult logout(@RequestParam String userId,HttpServletRequest request,HttpServletResponse response){
        //清除用户的相关信息的cookie
        CookieUtils.deleteCookie(request,response,"user");
        //TODO 用户退出登录，需要清空购物车
        //TODO 分布式会话中需要清除用户书数据

        return IMOOCJSONResult.ok();
    }



}