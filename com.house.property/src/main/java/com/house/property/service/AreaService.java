package com.house.property.service;

import com.house.property.entity.Area;
import com.house.property.service.base.BaseService;
import com.house.property.utils.TreeNode;

import java.util.List;

public interface AreaService extends BaseService<Area> {
    List<Area> getByParentId(Long parentId);
    List<TreeNode> getTreeArea();
}
