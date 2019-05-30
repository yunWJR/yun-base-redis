package com.yun.base.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author: yun
 * @createdOn: 2019-05-30 10:29.
 */

@ConfigurationProperties(prefix = "yun.redis.limit")
public class RedisLimitProperties {

    private int time;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
