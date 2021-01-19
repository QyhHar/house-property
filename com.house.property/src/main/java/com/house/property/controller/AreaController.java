package com.house.property.controller;

import com.house.property.entity.Area;
import com.house.property.entity.User;
import com.house.property.service.AreaService;
import com.house.property.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hang.qi
 * @program: property
 * @description: 区域接口
 * @date 2020-12-17 10:40:50
 */
@Slf4j
@RestController
@RequestMapping("/area")
public class AreaController {


    @Autowired
    private AreaService areaService;

    //初始化变量
    @PostConstruct
    public List<TreeNode> postConstruct(){
        log.info("初始化区域树");
        List<TreeNode> list = areaService.getTreeArea();
        RedisCache.putValue(EnumUtil.areaTreeCode,list,-1);
        return list;
    }


    /**
     * @Description: 获取区域树
     * @Author: hang.qi
     * @Date: 2020/12/17 0017 上午 10:44
     */
    @GetMapping("getTreeArea")
    public Response getTreeArea(){
        log.info("获取区域树");
        List<TreeNode> list = (List<TreeNode>)RedisCache.getValue(EnumUtil.areaTreeCode);
        if(list==null) list = postConstruct();
        return new Response(list);
    }

    /**
     * @Description: 获取子区域
     * @Author: hang.qi
     * @Date: 2020/12/23 0023 下午 4:24
     */
    @GetMapping("getChildArea")
    public Response getChildArea(String parentId){
        try {
            log.info("获取子区域；parentId"+parentId);
            List<Area> byParentId = areaService.getByParentId(parentId);
            return new Response(byParentId);
        }catch (Exception e){
            log.error("获取子区域出错",e);
        }
        return new Response(1,"获取子区域出错");
    }

    /**
     * @Description: 获取区域
     * @Author: hang.qi
     * @Date: 2020/12/23 0023 下午 4:24
     */
    @GetMapping("getAreaById")
    public Response getAreaById(Long id){
        try {
            log.info("获取区域；id"+id);
            Area byId = areaService.getById(id);
            return new Response(byId);
        }catch (Exception e){
            log.error("获取区域出错",e);
        }
        return new Response(1,"获取区域出错");
    }
}
