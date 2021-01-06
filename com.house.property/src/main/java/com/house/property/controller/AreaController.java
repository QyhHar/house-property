package com.house.property.controller;

import com.house.property.entity.Area;
import com.house.property.entity.User;
import com.house.property.service.AreaService;
import com.house.property.utils.MD5Util;
import com.house.property.utils.RedisCache;
import com.house.property.utils.Response;
import com.house.property.utils.TreeNode;
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


    public static String redisCode = "areaTree";
    @Autowired
    private AreaService areaService;

    //初始化变量
    @PostConstruct
    public List<TreeNode> postConstruct(){
        log.info("初始化区域树");
        List<TreeNode> list = areaService.getTreeArea();
        RedisCache.putValue(redisCode,list,-1);
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
        List<TreeNode> list = (List<TreeNode>)RedisCache.getValue(redisCode);
        if(list==null) list = postConstruct();
        return new Response(list);
    }

    /**
     * @Description: 获取子区域
     * @Author: hang.qi
     * @Date: 2020/12/23 0023 下午 4:24
     */
    @GetMapping("getChildArea")
    public Response getChildArea(Long parentId){
        try {
            log.info("获取子区域；parentId"+parentId);
            List<Area> byParentId = areaService.getByParentId(parentId);
            return new Response(byParentId);
        }catch (Exception e){
            log.error("获取子区域出错",e);
        }
        return new Response(1,"获取子区域出错");

    }
}
