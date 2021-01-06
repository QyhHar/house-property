package com.house.property.utils;

import java.util.UUID;

/**
 * @author: haoyu.dang
 * @date: 2020/2/11 11:02
 * @description:
 */
public class UUIDUtil {
    public static String createSessionUuid(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
