package com.house.property.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import com.house.property.entity.House;
import com.house.property.entity.HouseCollection;
import com.house.property.entity.User;
import com.house.property.service.HouseCollectionService;
import com.house.property.service.HouseService;
import com.house.property.service.UserService;
import com.house.property.utils.RedisCache;
import com.house.property.utils.Response;
import com.house.property.utils.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author hang.qi
 * @program: property
 * @description: 收藏接口
 * @date 2021-01-07 10:50:31
 */
@Slf4j
@RestController
@RequestMapping("/houseCollection")
public class HouseCollectionController {

    @Autowired
    private HouseCollectionService houseCollectionService;

    @Autowired
    private UserService userService;

    @Autowired
    private HouseService houseService;

    /**
     * @Description: 用户收藏房屋
     * @Author: hang.qi
     * @Date: 2021/1/7 0007 上午 10:57
     */
    @PostMapping("save")
    public Response save(@RequestBody HouseCollection houseCollection){
        try {
            log.info("用户收藏房屋");
            if(houseCollection.getHouseId()==null||houseCollection.getUserId()==null) return new Response(1,"参数不全");
            User byId = userService.getById(houseCollection.getUserId());
            House byId1 = houseService.getById(houseCollection.getHouseId());
            if(byId==null)return new Response(1,"用户不存在");
            if(byId1==null)return new Response(1,"房屋不存在");
            HouseCollection oneByUserIdHouseId = houseCollectionService.getOneByUserIdHouseId(houseCollection);
            if(oneByUserIdHouseId!=null) return new Response(1,"房屋已收藏");
            houseCollection.setAdd_time(new Date());
            houseCollectionService.save(houseCollection);
            return new Response("新增成功");
        }catch (Exception e){
            log.error("收藏房屋出错",e);
            return new Response(1,"收藏房屋出错");
        }
    }
    /**
     * @Description: 用户取消收藏房屋
     * @Author: hang.qi
     * @Date: 2021/1/7 0007 上午 10:57
     */
    @DeleteMapping("delete")
    public Response delete(@RequestBody HouseCollection houseCollection){
        try {
            log.info("取消收藏");
            HouseCollection oneByUserIdHouseId = houseCollectionService.getOneByUserIdHouseId(houseCollection);
            if(oneByUserIdHouseId==null) return new Response(1,"收藏不存在");
            houseCollectionService.delete(oneByUserIdHouseId.getId());
            return new Response("取消收藏成功");
        }catch (Exception e){
            log.error("取消收藏出错",e);
            return new Response(1,"取消收藏出错");
        }
    }


}
