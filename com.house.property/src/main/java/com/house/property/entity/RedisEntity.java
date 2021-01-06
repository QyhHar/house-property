package com.house.property.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author hang.qi
 * @program: property
 * @description: 模拟redis
 * @date 2020-12-31 14:51:37
 */
@Data
public class RedisEntity implements Serializable {


    private static final long serialVersionUID = -5464946618238367141L;
    /**
     * 存入的对象
     */
    private Object value;
    /**
     * 对象存入时 / 修改时的时间戳
     */
    private long modifyTime;
    /**
     * 过期时间 单位int:秒
     */
    private int expireTime;

    public RedisEntity() {
    }

    public RedisEntity(Object value, long modifyTime, int expireTime) {
        this.value = value;
        this.modifyTime = modifyTime;
        this.expireTime = expireTime;
    }
}
