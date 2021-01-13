package com.house.property.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.house.property.entity.*;
import com.house.property.service.*;
import com.house.property.service.base.BaseServiceImpl;
import com.house.property.service.impl.HouseServiceImpl;
import com.house.property.utils.ImageUtil;
import com.house.property.utils.MD5Util;
import com.house.property.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
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
    private HouseCollectionService houseCollectionService;
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
            QueryWrapper<House> queryWrapper = new QueryWrapper<>();
            if(Strings.isNotBlank(jsonObject.getString("desc"))){
                queryWrapper.orderByDesc(BaseServiceImpl.humpToLine(jsonObject.getString("desc")));
                jsonObject.remove("desc");
            }
            if(Strings.isNotBlank(jsonObject.getString("asc"))){
                queryWrapper.orderByAsc(BaseServiceImpl.humpToLine(jsonObject.getString("asc")));
                jsonObject.remove("asc");
            }
            Long userId = jsonObject.getLong("collectionUserId");
            Map<String, List<String>> houseByJson = getHouseByJson(jsonObject);
            IPage<House> houseIPage = houseService.selectPageByMap(page, houseByJson, queryWrapper);

            for (House house1: houseIPage.getRecords()) {
                if(userId!=null){
                    HouseCollection houseCollection  = new HouseCollection();
                    houseCollection.setHouseId(house1.getId());
                    houseCollection.setUserId(userId);
                    HouseCollection oneByUserIdHouseId = houseCollectionService.getOneByUserIdHouseId(houseCollection);
                    if(oneByUserIdHouseId!=null) house1.setIsCollection("1");
                    else house1.setIsCollection("0");
                }else {
                    house1.setIsCollection("0");
                }
                house1.setSumCollection(houseCollectionService.getCountUserCollection(house1.getId()));
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
        result.put("type",new ArrayList<String>(Collections.singleton(jsonObject.getString("type"))));//供暖
        if(!Strings.isEmpty(jsonObject.getString("residential")))
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
    public Response saveImageInfo(@RequestBody JSONObject jsonObject ){
        try {
            log.info("添加房屋信息");
            House house = jsonObject.toJavaObject(House.class);
            house.setAddTime(new Date());
            houseService.save(house);
            List<String> files = jsonObject.getJSONArray("file").toJavaList(String.class);
            for (String file: files) {
                Image image = new Image();
                image.setHouseId(house.getId());
                image.setImageUrl(file);
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
    public Response getById(Long id,Long userId){
        try {
            log.info("获取房屋根据Id；id="+id+"userId"+userId);
            House byUserId = houseService.getById(id);
            if(userId!=null){
                HouseCollection houseCollection  = new HouseCollection();
                houseCollection.setHouseId(id);
                houseCollection.setUserId(userId);
                HouseCollection oneByUserIdHouseId = houseCollectionService.getOneByUserIdHouseId(houseCollection);
                if(oneByUserIdHouseId!=null) byUserId.setIsCollection("1");
                else byUserId.setIsCollection("0");
            }else {
                byUserId.setIsCollection("0");
            }
            byUserId.setSumCollection(houseCollectionService.getCountUserCollection(id));
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
