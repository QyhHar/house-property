package com.house.property.service;

import com.house.property.entity.Area;
import com.house.property.service.base.BaseService;
import com.house.property.utils.TreeNode;

import java.util.List;
import java.util.Set;

public interface AreaService extends BaseService<Area> {
    List<Area> getByParentId(String parentId);
    List<TreeNode> getTreeArea();
    List<String> getAreaIdByParentId(List<String> parentIds);
}
