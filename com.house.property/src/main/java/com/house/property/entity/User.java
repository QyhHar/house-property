package com.house.property.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Description: 用户实体
 * @Author: hang.qi
 * @Date: 2020/12/16 0016 下午 4:30
 */
@TableName("yd_user")
@Data
public class User implements java.io.Serializable {


    private static final long serialVersionUID = 6088234006911030467L;

    @TableId(type = IdType.AUTO)
    private Long id;
    private String nickName;//昵称
    private String userName;//登录账号
    private String userPassword;//密码
    private String type; //账户类型：0-普通用户；1-超级管理员

}