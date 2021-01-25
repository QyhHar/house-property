package com.house.property.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.house.property.entity.*;
import com.house.property.service.*;
import com.house.property.service.base.BaseServiceImpl;
import com.house.property.service.impl.HouseServiceImpl;
import com.house.property.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Book;
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
     * @Description: 后台管理查询房源
     * @Author: hang.qi
     * @Date: 2021/1/19 0019 下午 1:54
     */
    @PostMapping("getHouseByPage")
    public Response getHouseByPage(@RequestBody JSONObject jsonObject){
        try {
            log.info("后台管理查询房源");
            Long current = jsonObject.getLong("current");
            Long size = jsonObject.getLong("size");
            if(current==null||size==null){
                return new Response(1,"参数不全");
            }
            IPage<House> page = new Page<>(current-1,size);
            QueryWrapper<House> queryWrapper = new QueryWrapper<>();
            queryWrapper.orderByDesc(BaseServiceImpl.humpToLine("addTime"));
            List<List<String>> list = jsonObject.get("areaId").equals("")?new ArrayList<>():(List<List<String>>)jsonObject.get("areaId");
            queryWrapper.lambda().in(list.size()>0,House::getAreaId,getChildAreaId(list))
                    .like(!Strings.isEmpty(jsonObject.getString("residential")),House::getResidential,jsonObject.getString("residential"));
            IPage<House> houseIPage = houseService.selectPageByCondition(page,  queryWrapper);
            for (House house: houseIPage.getRecords()) {
                house.setAreaName(areaService.getById(Long.parseLong(house.getAreaId())).getName());
            }
            return new Response(houseIPage);
        }catch (Exception e){
            log.error("后台管理查询房源出错",e);
        }
        return new Response(1,"后台管理查询房源出错");
    }

    /**
     * @Description: 后台管理
     * @Description: 删除房源接口
     * @Author: hang.qi
     * @Date: 2021/1/25 0025 下午 2:21
     */
    @DeleteMapping("delete/{ids}")
    public Response deleteHouse(@PathVariable("ids") String ids){
        try {
            log.info("删除房源接口;ids="+ids);
            List<Long> collect = Arrays.stream(ids.split(",")).map(Long::parseLong).collect(Collectors.toList());
            UpdateWrapper<HouseCollection> queryWrapper2 = new UpdateWrapper<>();
            queryWrapper2.lambda().in(collect.size()>0,HouseCollection::getHouseId,collect);
            houseCollectionService.delete(queryWrapper2);
            UpdateWrapper<Image> queryWrapper = new UpdateWrapper<>();
            queryWrapper.lambda().in(collect.size()>0,Image::getHouseId,collect);
            imageService.delete(queryWrapper);
            houseService.delete(collect);
            return new Response("删除房源成功");
        }catch (Exception e){
            log.error("删除房源出错",e);
        }
        return new Response(1,"删除房源出错");
    }


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
                List<Image> imageByHouseId = imageService.getImageByHouseId(house1.getId());
                if(imageByHouseId.size()>0)house1.setImages(imageByHouseId.subList(0,1));
            }
            return new Response(houseIPage);
        }catch (Exception e){
            log.error("获取用户绑定的所有房屋出错",e);
        }
        return new Response(1,"获取用户绑定的所有房屋出错");
    }

    public List<String> getChildAreaId(List<List<String>> lists){
        List<String> areaIds = new ArrayList<>();
        for (List<String> list: lists) {
            areaIds.addAll(list);
        }
        List<String> areaId1 = areaService.getAreaIdByParentId(areaIds);
        return areaId1.stream().map(x -> x + "").collect(Collectors.toList());
    }

    public Map<String,List<String>>  getHouseByJson (JSONObject jsonObject){
        List<String> areaId = getChildAreaId((List<List<String>>)jsonObject.get("areaId"));
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
     * @Description: 获取品质房屋
     * @Author: hang.qi
     * @Date: 2021/1/14 0014 上午 10:59
     */
    @GetMapping("getQualityHouse")
    public Response getQualityHouse(){
        try {
            log.info("获取品质房屋");
            Object qualityHouse = RedisCache.getValue(EnumUtil.qualityHouseCode);
            if(qualityHouse!=null){
                log.info("取缓存中的品质房屋");
                return new Response(qualityHouse);
            }
            List<House> list1  = getPageHouse("1");
            List<House> list2  = getPageHouse("2");
            for (House house:list1) {
                List<Image> imageByHouseId = imageService.getImageByHouseId(house.getId());
                house.setAreaName(areaService.getById(Long.parseLong(house.getAreaId())).getName());
                if(imageByHouseId.size()>0)house.setImages(new ArrayList<>(imageByHouseId.subList(0,1)));
            }for (House house:list2) {
                List<Image> imageByHouseId = imageService.getImageByHouseId(house.getId());
                house.setAreaName(areaService.getById(Long.parseLong(house.getAreaId())).getName());
                if(imageByHouseId.size()>0)house.setImages(new ArrayList<>(imageByHouseId.subList(0,1)));
            }
            Map<String,List<House>> result = new HashMap<>();
            result.put("buy",list1);
            result.put("rent",list2);
            RedisCache.putValue(EnumUtil.qualityHouseCode,result,3*60);
            return new Response(result);
        }catch (Exception e){
            log.error("获取用户绑定的所有房屋出错",e);
        }
        return new Response(1,"获取用户绑定的所有房屋出错");
    }

    public List<House> getPageHouse(String type){
        IPage<House> page = new Page<>(0,10);
        QueryWrapper<House> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc(BaseServiceImpl.humpToLine("addTime"));
        queryWrapper.lambda().eq(House::getType,type);
        IPage<House> houseIPage = houseService.selectPageByCondition(page,  queryWrapper);
        Set<House> set = new TreeSet<>(new Comparator<House>() {
            @Override
            public int compare(House o1, House o2) {
                if(o1.getSumCollection().equals(o2.getSumCollection())) return 1;
                return o2.getSumCollection()-o1.getSumCollection();
            }
        });
        for (House house: houseIPage.getRecords()) {
            house.setSumCollection(houseCollectionService.getCountUserCollection(house.getId()));
        }
        set.addAll(houseIPage.getRecords());
        List<House> list = new ArrayList<>(set);
        return new ArrayList<House>(list.subList(0,Math.min(set.size(),4)));
    }


    /**
     * @Description: 获取用户绑定的所有房屋
     * @Author: hang.qi
     * @Date: 2020/12/16 0016 下午 5:39
     */
    @GetMapping("getByUserId")
    public Response getByUserId(Long userId,String residential,Integer current,Integer size){
        try {
            if(current==null||size==null){
                return new Response(1,"参数不全");
            }
            log.info("获取用户绑定的所有房屋；userId="+userId+",residential="+residential);
            IPage<House> page = new Page<>(current-1,size);
            QueryWrapper<House> queryWrapper = new QueryWrapper<>();
            queryWrapper.orderByDesc(BaseServiceImpl.humpToLine("addTime"));
            queryWrapper.lambda().eq(House::getUserId,userId).like(!Strings.isEmpty(residential),House::getResidential,residential);
            IPage<House> houseIPage = houseService.selectPageByCondition(page, queryWrapper);
            for (House house: houseIPage.getRecords()) {
                List<Image> imageByHouseId = imageService.getImageByHouseId(house.getId());
                if(imageByHouseId.size()>0)house.setImages(imageByHouseId.subList(0,1));
            }
            return new Response(houseIPage);
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
            List<Image> list = JSONArray.parseArray(jsonObject.getJSONArray("images").toJSONString(),Image.class);
            for (Image image: list) {
                image.setHouseId(house.getId());
                image.setName(image.getName().substring(0,image.getName().lastIndexOf(".")));
                imageService.save(image);
            }
            return new Response("添加房屋信息成功");
        }catch (Exception e){
            log.error("添加房屋信息出错",e);
        }
        return new Response(1,"添加房屋信息出错");
    }

    @PutMapping("/update")
    public Response updateImageInfo(@RequestBody JSONObject jsonObject ){
        try {
            log.info("添加房屋信息");
            House house = jsonObject.toJavaObject(House.class);
            house.setAddTime(new Date());
            houseService.update(house);
            UpdateWrapper<Image> queryWrapper = new UpdateWrapper<>();
            queryWrapper.lambda().eq(Image::getHouseId,house.getId());
            imageService.delete(queryWrapper);
            List<Image> list = JSONArray.parseArray(jsonObject.getJSONArray("images").toJSONString(),Image.class);
            for (Image image: list) {
                image.setHouseId(house.getId());
                if(image.getName().contains("."))image.setName(image.getName().substring(0,image.getName().lastIndexOf(".")));
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
            log.info("获取房屋根据Id；id="+id+" userId="+userId);
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
            byUserId.setAreaName(areaService.getById(Long.parseLong(byUserId.getAreaId())).getName());
            byUserId.setSumCollection(houseCollectionService.getCountUserCollection(id));
            List<Image> imageByHouseId = imageService.getImageByHouseId(id);
            byUserId.setImages(imageByHouseId);
            User byId = userService.getById(byUserId.getUserId());
            Map<String,Object> result = new HashMap<>();
            result.put("house",byUserId);
            result.put("user",byId);
            return new Response(result);
        }catch (Exception e){
            log.error("获取房屋根据Id出错",e);
        }
        return new Response(1,"获取房屋根据Id出错");
    }



}
