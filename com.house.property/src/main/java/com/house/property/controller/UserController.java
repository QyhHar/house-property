package com.house.property.controller;

import com.house.property.entity.User;
import com.house.property.mapper.UserMapper;
import com.house.property.service.UserService;
import com.house.property.utils.MD5Util;
import com.house.property.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        return new Response(1,"登录名不合法");
    }

    /**
     * @Description: 登录
     * @Author: hang.qi
     * @Date: 2020/12/16 0016 下午 5:36
     */
    @PostMapping("login")
    public Response signIn(@RequestBody User user){
        try {
            log.info("登录接口");

            User userByUserName = userService.getUserByUserName(user.getUserName());
            if(userByUserName==null) return new Response(1,"用户名和密码不匹配");
            if(StringUtils.equals(userByUserName.getUserPassword(),MD5Util.getMD5Str(user.getUserPassword()))){
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
            user.setUserPassword(MD5Util.getMD5Str(user.getUserPassword()));
            userService.save(user);
            return new Response("注册成功");
        }catch (Exception e){
            log.error("注册出错",e);
        }
        return new Response(1,"注册出错");
    }


}
