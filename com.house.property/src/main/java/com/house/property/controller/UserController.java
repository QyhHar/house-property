package com.house.property.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.house.property.entity.House;
import com.house.property.entity.User;
import com.house.property.service.UserService;
import com.house.property.service.base.BaseServiceImpl;
import com.house.property.utils.MD5Util;
import com.house.property.utils.RedisCache;
import com.house.property.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
     * @Description: 获取所有的user
     * @Author: hang.qi
     * @Date: 2021/1/18 0018 下午 6:01
     */
    @GetMapping("getUserByQuery")
    public Response getUserByQuery(String userName,String nickName,Integer current,Integer size){
        log.info("获取所有的user");
        IPage<User> page = new Page<>(current-1,size);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().like(!Strings.isEmpty(userName),User::getUserName,userName).like(!Strings.isEmpty(nickName),User::getNickName,nickName);
        IPage<User> userIPage = userService.selectPageByCondition(page, queryWrapper);
        return new Response(userIPage);
    }

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
    public Response register(@RequestBody JSONObject jsonObject){
        try {
            log.info("注册接口");
            Boolean aBoolean = captchaImageController.checkCode(jsonObject.getString("uuid"), jsonObject.getString("code"));
            if(!aBoolean)return new Response(1,"验证码不正确");
            User user = jsonObject.toJavaObject(User.class);
            user.setPassword(MD5Util.getMD5Str(user.getPassword()));
            userService.save(user);
            return new Response("注册成功");
        }catch (Exception e){
            log.error("注册出错",e);
        }
        return new Response(1,"注册出错");
    }
    /**
     * @Description: 新增用户接口`
     * @Author: hang.qi
     * @Date: 2021/1/19 0019 上午 10:41
     */
    @PostMapping("save")
    public Response save(@RequestBody JSONObject jsonObject){
        try {
            log.info("新增用户接口");
            User user = jsonObject.toJavaObject(User.class);
            user.setPassword(MD5Util.getMD5Str(user.getPassword()));
            userService.save(user);
            return new Response("新增用户成功");
        }catch (Exception e){
            log.error("新增用户出错",e);
        }
        return new Response(1,"新增用户出错");
    }
    /**
     * @Description: 更新用户接口
     * @Author: hang.qi
     * @Date: 2021/1/19 0019 上午 10:31
     */
    @PutMapping("update")
    public Response update(@RequestBody JSONObject jsonObject){
        try {
            log.info("更新用户接口");
            User user = jsonObject.toJavaObject(User.class);
            User userByUserName = userService.getUserByUserName(jsonObject.getString("userName"));
            if(!userByUserName.getPassword().equals(user.getPassword()))
            user.setPassword(MD5Util.getMD5Str(user.getPassword()));
            userService.update(user);
            return new Response("更新用户成功");
        }catch (Exception e){
            log.error("更新用户出错",e);
        }
        return new Response(1,"更新用户出错");
    }
    /**
     * @Description: 修改密码
     * @Author: hang.qi
     * @Date: 2021/1/19 0019 上午 10:31
     */
    @PutMapping("updatePassword")
    public Response updatePassword(@RequestBody JSONObject jsonObject){
        try {
            log.info("修改密码接口");
            User userByUserName = userService.getUserByUserName(jsonObject.getString("userName"));
            if(userByUserName==null) return new Response(1,"用户名不存在");
            userByUserName.setNickName(jsonObject.getString("nickName"));
            if(StringUtils.equals(userByUserName.getPassword(),MD5Util.getMD5Str(jsonObject.getString("oldPassword")))){
                userByUserName.setPassword(MD5Util.getMD5Str(jsonObject.getString("newPassword")));
                userService.update(userByUserName);
            }else {
                return new Response(1,"用户名和密码不匹配");
            }
            return new Response(userByUserName);
        }catch (Exception e){
            log.error("修改密码出错",e);
        }
        return new Response(1,"修改密码出错");
    }
    /**
     * @Description: 删除用户
     * @Author: hang.qi
     * @Date: 2021/1/19 0019 上午 10:59
     */
    @DeleteMapping("delete/{ids}")
    public Response delete(@PathVariable("ids") String ids){
        try {
            log.info("删除用户接口;ids="+ids);
            List<Long> collect = Arrays.stream(ids.split(",")).map(Long::parseLong).collect(Collectors.toList());
            userService.delete(collect);
            return new Response("删除用户成功");
        }catch (Exception e){
            log.error("删除用户出错",e);
        }
        return new Response(1,"删除用户出错");
    }


}
