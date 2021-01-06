package com.house.property.controller;

import com.alibaba.fastjson.JSONObject;
import com.house.property.entity.User;
import com.house.property.service.UserService;
import com.house.property.utils.MD5Util;
import com.house.property.utils.RedisCache;
import com.house.property.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author hang.qi
 * @program: property
 * @description: 用户接口
 * @date 2020-12-16 15:09:11
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private CaptchaImageController captchaImageController;

    /**
     * @Description: 校验登录,
     * @Author: hang.qi
     * @Date: 2020/12/16 0016 下午 5:39
     */
    @GetMapping("getUserName")
    public Response getUserName(String userName){
        log.info("校验登录名；userName="+userName);
        User userByUserName = userService.getUserByUserName(userName);
        if(userByUserName==null) return new Response(0,"登录名合法");
        return new Response(1,"登录名已存在");
    }

    /**
     * @Description: 登录
     * @Author: hang.qi
     * @Date: 2020/12/16 0016 下午 5:36
     */
    @PostMapping("login")
    public Response signIn(@RequestBody JSONObject jsonObject){
        try {
            log.info("登录接口");
            Boolean aBoolean = captchaImageController.checkCode(jsonObject.getString("uuid"), jsonObject.getString("code"));
            if(!aBoolean)return new Response(1,"验证码不正确");
            User userByUserName = userService.getUserByUserName(jsonObject.getString("userName"));
            if(userByUserName==null) return new Response(1,"用户名和密码不匹配");
            if(StringUtils.equals(userByUserName.getPassword(),MD5Util.getMD5Str(jsonObject.getString("password")))){
                return new Response(userByUserName);
            }
            return new Response(1,"用户名和密码不匹配");
        }catch (Exception e){
            log.error("登录出错",e);
        }
        return new Response(1,"登录出错");
    }

    /**
     * @Description: 注册
     * @Author: hang.qi
     * @Date: 2020/12/16 0016 下午 5:37
     */
    @PostMapping("register")
    public Response register(@RequestBody User user){
        try {
            log.info("注册接口");
            user.setPassword(MD5Util.getMD5Str(user.getPassword()));
            userService.save(user);
            return new Response("注册成功");
        }catch (Exception e){
            log.error("注册出错",e);
        }
        return new Response(1,"注册出错");
    }


}
