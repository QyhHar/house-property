package com.house.property.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.house.property.entity.Area;
import com.house.property.entity.House;
import com.house.property.entity.Image;
import com.house.property.entity.User;
import com.house.property.service.AreaService;
import com.house.property.service.HouseService;
import com.house.property.service.ImageService;
import com.house.property.service.UserService;
import com.house.property.utils.ImageUtil;
import com.house.property.utils.MD5Util;
import com.house.property.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

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
    @Autowired
    private AreaService areaService;

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
            Map<String, List<String>> houseByJson = getHouseByJson(jsonObject);
            QueryWrapper<House> queryWrapper = new QueryWrapper<>();
            IPage<House> houseIPage = houseService.selectPageByMap(page, houseByJson, queryWrapper);
            log.info("查询sql："+queryWrapper.getSqlSelect());

            for (House house1: houseIPage.getRecords()) {
                house1.setImages(imageService.getImageByHouseId(house1.getId()));
            }
            return new Response(houseIPage);
        }catch (Exception e){
            log.error("获取用户绑定的所有房屋出错",e);
        }
        return new Response(1,"获取用户绑定的所有房屋出错");
    }

    public Map<String,List<String>>  getHouseByJson (JSONObject jsonObject){
        List<List<String>> areas =  (List<List<String>>)jsonObject.get("areaId");
        List<String> areaIds = new ArrayList<>();
        for (List<String> list: areas) {
            areaIds.addAll(list);
        }
        List<String> areaId1 = areaService.getAreaIdByParentId(areaIds);
        List<String> areaId = areaId1.stream().map(x -> x + "").collect(Collectors.toList());
        Map<String,List<String>> result = new HashMap<>();
        result.put("totalPrice",((List<String>)jsonObject.get("totalPrice")));    //价格
        result.put("room",((List<String>)jsonObject.get("room")));                    //几室
        result.put("measureArea",((List<String>)jsonObject.get("measureArea")));     //大小
        result.put("areaId",areaId);                                                    //区域
        result.put("orientation",((List<String>)jsonObject.get("orientation")));    //朝向
        result.put("buildingAge",((List<String>)jsonObject.get("buildingAge")));//楼龄
        result.put("purpose",((List<String>)jsonObject.get("purpose")));//用途
        result.put("heating",((List<String>)jsonObject.get("heating")));//供暖
        if(!StringUtils.isEmpty(jsonObject.getString("residential")))
        result.put("residential",new ArrayList<String>(Collections.singleton(jsonObject.getString("residential"))));
        return result;
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
    public Response saveImageInfo(@RequestParam(value = "file",required = false) MultipartFile[] files,@RequestParam(value = "obj") String obj){
        try {
            House house = JSONObject.parseObject(obj).toJavaObject(House.class);;
            house.setAddTime(new Date());
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
