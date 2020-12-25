package com.house.property.controller;

import com.alibaba.fastjson.JSONObject;
import com.house.property.entity.Area;
import com.house.property.entity.House;
import com.house.property.entity.Image;
import com.house.property.entity.User;
import com.house.property.service.HouseService;
import com.house.property.service.ImageService;
import com.house.property.service.UserService;
import com.house.property.utils.ImageUtil;
import com.house.property.utils.MD5Util;
import com.house.property.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author hang.qi
 * @program: property
 * @description: 房屋接口
 * @date 2020-12-16 15:09:11
 */
@Slf4j
@RestController
@RequestMapping("/house")
public class HouseController {

    @Autowired
    private HouseService houseService;
    @Autowired
    private ImageService imageService;

    /**
     * @Description: 获取用户绑定的所有房屋
     * @Author: hang.qi
     * @Date: 2020/12/16 0016 下午 5:39
     */
    @GetMapping("getByUserId")
    public Response getUserName(Long userId){
        try {
            log.info("获取用户绑定的所有房屋；userId="+userId);
            List<House> byUserId = houseService.getByUserId(userId);
            return new Response(byUserId);
        }catch (Exception e){
            log.error("获取用户绑定的所有房屋出错",e);
        }
        return new Response(1,"获取用户绑定的所有房屋出错");
    }
    /**
     * @Description: 添加房屋信息
     * @Author: hang.qi
     * @Date: 2020/12/24 0024 下午 5:58
     */
    @PostMapping("/save")
    public Response saveImageInfo(@RequestParam(value = "file") List<MultipartFile> files, @RequestBody JSONObject jsonObject){
        try {
            House house = JSONObject.toJavaObject(jsonObject, House.class);
            houseService.save(house);
            for (MultipartFile file: files) {
                String url = ImageUtil.saveImage(file);
                Image image = new Image();
                image.setHouseId(house.getId());
                image.setImageUrl(url);
                imageService.save(image);
            }
            return new Response("添加房屋信息成功");
        }catch (Exception e){
            log.error("添加房屋信息出错",e);
        }
        return new Response(1,"添加房屋信息出错");
    }




}
