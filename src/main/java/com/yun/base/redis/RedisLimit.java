package com.yun.base.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: yun
 * @createdOn: 2019-05-29 15:19.
 */

@Component
public class RedisLimit {

    /**
     * 失败编码-触发了限流
     */
    private static final int FAIL_CODE = 0;

    @Autowired
    private RedisLimitProperties properties;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private Map<String, RedisLimitPara> paraMap;

    @PostConstruct
    public void init() {
        paraMap = new HashMap<String, RedisLimitPara>();
    }

    public void checkLimit() {
        checkLimit(null);
    }

    public void checkLimit(String key, int expireTime, int duration, int limitCount) {
        RedisLimitPara para = getByKey(key, expireTime, duration, limitCount);

        Object result = limitRequest(para);

        if (FAIL_CODE == (Long) result) {
            throw new YunLimitException(para);
        }
    }

    public void checkLimit(String key) {
        RedisLimitPara para = getByKey(key, properties.getExpire_time(), properties.getDuration(), properties.getLimitCount());

        Object result = limitRequest(para);

        if (FAIL_CODE == (Long) result) {
            throw new YunLimitException(para);
        }
    }

    private RedisLimitPara getByKey(String key, int expireTime, int duration, int limitCount) {
        RedisLimitPara para = paraMap.get(key);
        if (para == null) {
            para = new RedisLimitPara(key, properties.getBaseKey(), expireTime, duration, limitCount);

            paraMap.put(key, para);
        }

        return para;
    }

    private Object limitRequest(RedisLimitPara para) {
        Object result;

        long timeKey = System.currentTimeMillis() / 1000; // 按秒计算
        timeKey = timeKey / para.getDuration();

        // 时间作为 key
        String key = String.valueOf(timeKey);
        List<String> keys = Collections.singletonList(key);

        // 脚本
        DefaultRedisScript<Long> redisScript;
        redisScript = new DefaultRedisScript<Long>();
        redisScript.setResultType(Long.class);
        redisScript.setScriptText(para.getScript());

        result = redisTemplate.execute(redisScript, keys, String.valueOf(para.getLimitCount()), String.valueOf(para.getExpire_time()));

        return result;
    }
}
