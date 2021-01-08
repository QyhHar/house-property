package com.house.property.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author hang.qi
 * @program: property
 * @description: 房屋收藏表
 * @date 2021-01-07 10:44:08
 */
@TableName("yd_house_collection")
@Data
public class HouseCollection implements Serializable {

    private static final long serialVersionUID = -4194219316659772158L;
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;//区域id
    private Long houseId;//小区
    private Date add_time;//房屋地址
}
