package com.yun.base.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;

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
    private RedisTemplate<String, Object> redisTemplate1;

    private int limit = 1;
    private int expire_time = 20;

    private String baseID = "limit";

    /**
     * 脚本
     */
    private String script;

    @PostConstruct
    public void init() {
        buildScript();
    }

    public boolean checkLimit() {
        Object result = limitRequest();

        if (FAIL_CODE != (Long) result) {
            return true;
        } else {
            return false;
        }
    }

    private Object limitRequest() {
        Object result = null;
        String key = String.valueOf(System.currentTimeMillis() / 1000); // 按秒计算

        List<String> keys = Collections.singletonList(key);

        DefaultRedisScript<Long> redisScript;
        redisScript = new DefaultRedisScript<Long>();
        redisScript.setResultType(Long.class);
        redisScript.setScriptText(script);

        result = redisTemplate1.execute(redisScript, keys, String.valueOf(limit), String.valueOf(expire_time));

        return result;
    }

    private void buildScript() {
        script = "local key = \"epmg.api.limit:\" .. KEYS[1]\n" +
                "local limit = tonumber(ARGV[1])\n" +
                "local expire_time = ARGV[2]\n" +
                "\n" +
                "local is_exists = redis.call(\"EXISTS\", key)\n" +
                "if is_exists == 1 then\n" +
                "    if redis.call(\"INCR\", key) > limit then\n" +
                "        return 0\n" +
                "    else\n" +
                "        return 1\n" +
                "    end\n" +
                "else\n" +
                "    redis.call(\"SET\", key, 1)\n" +
                "    redis.call(\"EXPIRE\", key, expire_time)\n" +
                "    return 1\n" +
                "end";
    }


}
