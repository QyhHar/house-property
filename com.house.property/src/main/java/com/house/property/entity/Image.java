package com.house.property.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author hang.qi
 * @program: property
 * @description: 图片实体
 * @date 2020-12-24 17:46:09
 */
@TableName("yd_image")
@Data
public class Image implements java.io.Serializable {


    private static final long serialVersionUID = -5431848296646477252L;
    @TableId(type = IdType.AUTO)
    private Long id;
    private String imageUrl;//图片地址
    private Long houseId;//房屋ID
}
