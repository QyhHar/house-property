package com.house.property.utils;


import lombok.Data;

import java.util.List;

/**
 * @Description: 树形结构
 * @Author: hang.qi
 * @Date: 2020/12/17 0017 上午 10:28
 */
@Data
public class TreeNode {
    private Long id;
    private String name;
    private Long parentId;
    private List<TreeNode> children;
}
