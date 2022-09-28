package com.zf.utils;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @Author wenqin
 * @Date 2022/9/26 20:43
 */
@Component
public class ExposureUtils {
    public static final String VISITOR = "visitor";
    public static final String DOWNLOAD = "download";
    public static final String ADD_CONTACT = "addContact";
    public static final String NOTES = "notes";
    public static final String FORWARD = "forward";

    @Resource
    private RedisCache redisCache;

    /**
     *
     * @param key 曝光属性值
     * @param id 用户id
     */
    public void incrementExposureOne(String key,Integer id){
        String KEY = "exposure:" + id + ":" + key + ":";
        Integer count = redisCache.getCacheObject(KEY);
        if (Objects.isNull(count)){
            redisCache.setCacheObject(KEY,1);
            return;
        }
        redisCache.setCacheObject(KEY,++count);
    }

    public Integer getExposureCount(String key,Integer id){
        String KEY = "exposure:" + id + ":" + key + ":";
        return redisCache.getCacheObject(KEY);
    }

}
