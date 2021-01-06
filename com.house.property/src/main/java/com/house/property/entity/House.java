package com.house.property.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.io.Serializable;
import java.util.List;

/**
 * @Description: 房屋实体
 * @Author: hang.qi
 * @Date: 2020/12/24 0024 下午 4:36
 */
@TableName("yd_house")
@Data
public class House implements Serializable {


    private static final long serialVersionUID = -5300357008809488294L;
    @TableId(type = IdType.AUTO)
    private Long id;
    private Integer areaId;//区域id
    private String residential;//小区
    private String houseAddress;//房屋地址
    private String unitPrice;//单价
    private String measureArea;//面积
    private String totalPrice;//总价
    private Integer room;//几室
    private Integer office;//几厅
    private String orientation;//朝向：1-北；2-南；3-西；4-东
    private String floor;//几楼
    private String buildingAge;//楼龄
    private String purpose;//用途：1-普通住宅；2-商业类；3-别墅；4四合院；5-车位；6-其他
    private String heating;//供暖：1-集中供暖；2-自供暖
    private String type;//类型：1:-买卖；2-出租
    private String rentalType;//出租方式：1:-整租；2-合租
    private String rent;//租金(每月)
    private String phoneNumber;//手机号码
    private Long userId;//用户Id

    @TableField(exist=false)
    private List<Image> images;

}