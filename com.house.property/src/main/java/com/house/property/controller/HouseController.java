package com.house.property.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private UserService userService;

    /**
     * @Description: 条件查询房屋信息分页
     * @Author: hang.qi
     * @Date: 2020/12/31 0031 下午 5:06
     */
    @PostMapping("getByQuery")
    public Response getByQuery(@RequestBody JSONObject jsonObject){
        try {
            log.info("条件查询房屋信息分页");
            Long current = jsonObject.getLong("current");
            Long size = jsonObject.getLong("size");
            if(current==null||size==null){
                return new Response(1,"参数不全");
            }
            IPage<House> page = new Page<>(current-1,size);
            House house = jsonObject.toJavaObject(House.class);
            QueryWrapper<House> queryWrapper = new QueryWrapper<>();
            IPage<House> houseIPage = houseService.selectPageByQuery(page, house, queryWrapper);
            for (House house1: houseIPage.getRecords()) {
                house1.setImages(imageService.getImageByHouseId(house1.getId()));
            }
            return new Response(houseIPage);
        }catch (Exception e){
            log.error("获取用户绑定的所有房屋出错",e);
        }
        return new Response(1,"获取用户绑定的所有房屋出错");
    }

    /**
     * @Description: 获取用户绑定的所有房屋
     * @Author: hang.qi
     * @Date: 2020/12/16 0016 下午 5:39
     */
    @GetMapping("getByUserId")
    public Response getByUserId(Long userId){
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
    /**
     * @Description: 获取房屋根据Id
     * @Author: hang.qi
     * @Date: 2020/12/16 0016 下午 5:39
     */
    @GetMapping("getById")
    public Response getById(Long id){
        try {
            log.info("获取房屋根据Id；id="+id);
            House byUserId = houseService.getById(id);
            List<Image> imageByHouseId = imageService.getImageByHouseId(id);
            User byId = userService.getById(byUserId.getUserId());
            Map<String,Object> result = new HashMap<>();
            result.put("house",byUserId);
            result.put("image",imageByHouseId);
            result.put("user",byId);
            return new Response(result);
        }catch (Exception e){
            log.error("获取房屋根据Id出错",e);
        }
        return new Response(1,"获取房屋根据Id出错");
    }



}
