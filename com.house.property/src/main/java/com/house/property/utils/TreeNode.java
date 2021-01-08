package com.house.property.utils;


import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 树形结构
 * @Author: hang.qi
 * @Date: 2020/12/17 0017 上午 10:28
 */
@Data
public class TreeNode implements Serializable {

    private static final long serialVersionUID = -8851339945708415816L;
    private String value;
    private String label;
    private Long parentValue;
    private List<TreeNode> children;
}
