package com.house.property.utils;

import com.house.property.entity.RedisEntity;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author hang.qi
 * @program: property
 * @description: redis工具
 * @date 2020-12-31 14:53:04
 */
@Slf4j
public class RedisCache {
    //默认的缓存容量
    private static int DEFAULT_CAPACITY = 512;
    //最大容量
    private static int MAX_CAPACITY = 100000;
    //刷新缓存的频率   秒
    private static int MONITOR_DURATION = 10;
    // 启动监控线程
    static {
        new Thread(new TimeoutTimerThread()).start();
    }
    //使用默认容量创建一个Map
    public static ConcurrentHashMap<String, RedisEntity> cache = new ConcurrentHashMap<String, RedisEntity>(
            DEFAULT_CAPACITY);

    /**
     * 将key-value 保存到本地缓存并制定该缓存的过期时间
     *
     * @param key
     * @param value
     * @param expireTime 过期时间，如果是-1 则表示永不过期  单位秒
     * @return
     */
    public static boolean putValue(String key, Object value, int expireTime) {
        return putCloneValue(key, value, expireTime);
    }

    /**
     * 将值通过序列化clone 处理后保存到缓存中，可以解决值引用的问题
     *
     * @param key
     * @param value
     * @param expireTime
     * @return
     */
    private static boolean putCloneValue(String key, Object value, int expireTime) {
        try {
            if (cache.size() >= MAX_CAPACITY) {
                return false;
            }
            // 序列化赋值
            RedisEntity entityClone = clone(new RedisEntity(value, System.nanoTime(), expireTime));
            cache.put(key, entityClone);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     *
     * 序列化 克隆处理
     * @param object
     * @return
     */
    private static <T extends Serializable> T clone(T object) {
        T cloneObject = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            oos.close();
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            cloneObject = (T) ois.readObject();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cloneObject;
    }

    /**
     *从本地缓存中获取key对应的值，如果该值不存在则返回null
     *
     * @param key
     * @return
     */
    public static Object getValue(String key) {
        if(cache.get(key)==null) return null;
        return cache.get(key).getValue();
    }

    /**
     * 清空所有
     */
    public static void clear() {
        cache.clear();
    }

    /**
     * 过期处理线程
     *
     * @author Lenovo
     * @version
     */
    static class TimeoutTimerThread implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(MONITOR_DURATION);
                    checkTime();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * 过期缓存的具体处理方法
         * @throws Exception
         */
        private void checkTime() throws Exception {
            //"开始处理过期 ";
            for (String key : cache.keySet()) {
                RedisEntity tce = cache.get(key);
                long timoutTime = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime()
                        - tce.getModifyTime());
                //" 过期时间 : "+timoutTime);
                if (tce.getExpireTime()==-1||tce.getExpireTime() > timoutTime) {
                    continue;
                }
                log.info(" 清除过期缓存 ： " + key);
                //清除过期缓存和删除对应的缓存队列
                cache.remove(key);
            }
        }
    }
}
