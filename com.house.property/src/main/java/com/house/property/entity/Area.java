package com.house.property.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Description: 区域实体
 * @Author: hang.qi
 * @Date: 2020/12/16 0016 下午 4:30
 */
@TableName("yd_area")
@Data
public class Area implements java.io.Serializable {


    private static final long serialVersionUID = -5720645315623353870L;
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;//区域名
    private Long parentId; //父节点ID

}