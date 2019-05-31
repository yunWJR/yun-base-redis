package com.yun.base.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author: yun
 * @createdOn: 2019-05-30 10:29.
 */

@ConfigurationProperties(prefix = "yun.redis.limit")
public class RedisLimitProperties {
    /**
     * redis 存储 key的 base 值
     */
    private String baseKey = "limit";

    /**
     * 过期时间 s
     */
    private int expire_time = 1;

    /**
     * 限制时间 s
     */
    private int duration = 1;

    /**
     * 限制次数
     */
    private int limitCount = 100;

    // region --Getter and Setter

    public String getBaseKey() {
        return baseKey;
    }

    public void setBaseKey(String baseKey) {
        this.baseKey = baseKey;
    }

    public int getExpire_time() {
        return expire_time;
    }

    public void setExpire_time(int expire_time) {
        this.expire_time = expire_time;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getLimitCount() {
        return limitCount;
    }

    public void setLimitCount(int limitCount) {
        this.limitCount = limitCount;
    }

    // endregion
}
