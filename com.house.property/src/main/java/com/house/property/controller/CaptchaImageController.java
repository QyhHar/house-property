package com.house.property.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import com.house.property.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * @author hang.qi
 * @program: property
 * @description: 图片验证码
 * @date 2020-12-30 16:33:18
 */
@Slf4j
@RestController
@RequestMapping("/captcha")
public class CaptchaImageController {


    private String redisCode = "captcha";


    /**
     * @Description: 获取图片验证码
     * @Author: hang.qi
     * @Date: 2020/12/31 0031 下午 3:13
     */
    @GetMapping("getCode")
    public Response captchaImage(Integer width,Integer height){
        try {
            log.info("获取图片验证码");
            if(width==null||height==null) return new Response(1,"参数不全");
            ShearCaptcha shearCaptcha = CaptchaUtil.createShearCaptcha(width, height);
            Lock lock = new ReentrantLock();
            lock. lock();

            String code = shearCaptcha.getCode();
            String uuid = UUIDUtil.createSessionUuid();
            RedisCache.putValue(redisCode+uuid,code,3*60);
            Map<String,String> result = new HashMap<>();
            result.put("uuid",uuid);
            result.put("captchaCode","data:img/jpg;base64,"+shearCaptcha.getImageBase64());
            return new Response(result);
        }catch (Exception e){
            log.error("获取图片验证码出错",e);
            return new Response(1,"获取图片验证码出错");
        }
    }

    /**
     * @Description: 校验验证码
     * @Author: hang.qi
     * @Date: 2020/12/31 0031 下午 3:20
     */
    public Boolean checkCode(String uuid,String code){
        log.info("校验验证码");
        if(RedisCache.getValue(redisCode + uuid)==null) return false;
        String value = (String)RedisCache.getValue(redisCode + uuid);
        return StringUtils.equals(value,code);
    }

}
