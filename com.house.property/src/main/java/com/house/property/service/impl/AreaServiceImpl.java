package com.house.property.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.house.property.entity.Area;
import com.house.property.mapper.AreaMapper;
import com.house.property.service.AreaService;
import com.house.property.service.base.BaseServiceImpl;
import com.house.property.utils.TreeNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hang.qi
 * @program: property
 * @description: 区域Service
 * @date 2020-12-17 10:30:27
 */
@Slf4j
@Service
public class AreaServiceImpl extends BaseServiceImpl<Area> implements AreaService {

    @Autowired
    private AreaMapper areaMapper;

    @Override
    public List<Area> getByParentId(Long parentId) {
        QueryWrapper<Area> query = new QueryWrapper<>();
        query.lambda().eq(Area::getParentId,parentId);
        return areaMapper.selectList(query);
    }

    @Override
    public TreeNode getTreeArea() {
        Area area = areaMapper.selectById(0L);
        TreeNode treeNode = new TreeNode();
        treeNode.setId(area.getId());
        treeNode.setName(area.getName());
        treeNode.setParentId(0L);
        treeNode.setChildren(getChildArea(area.getId()));
        return treeNode;
    }

    public List<TreeNode> getChildArea(Long parentId){
        List<TreeNode> treeNodes  = new ArrayList<>();
        List<Area> byParentId = getByParentId(parentId);
        for (Area area: byParentId) {
            TreeNode treeNode = new TreeNode();
            treeNode.setId(area.getId());
            treeNode.setName(area.getName());
            treeNode.setParentId(area.getParentId());
            treeNode.setChildren(getChildArea(area.getId()));
            treeNodes.add(treeNode);
        }
        return treeNodes;
    }
}
