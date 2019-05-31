package com.yun.base.redis;

/**
 * @author: yun
 * @createdOn: 2019-05-31 15:59.
 */

public class RedisLimitPara {
    /**
     * redis 存储 key 值
     */
    private String key;

    /**
     * redis 存储 key的 base 值
     */
    private String baseKey;

    /**
     * redis 存储 key 值
     */
    private String allKey;

    /**
     * 过期时间 s
     */
    private int expire_time;

    /**
     * 限制时间 s
     */
    private int duration;

    /**
     * 脚本
     */
    private String script;

    /**
     * 限制次数
     */
    private int limitCount;

    public RedisLimitPara(String key, String baseKey, int expireTime, int duration, int limitCount) {
        this.key = key;

        this.baseKey = baseKey;

        this.allKey = this.baseKey + (key == null ? "" : "." + key); // com.yun.base.redis

        this.duration = duration;

        this.expire_time = expireTime + this.duration; // 过期时间为持续时间+过期

        this.limitCount = limitCount;

        buildScript();
    }

    private void buildScript() {
        this.script = "local key = \"" + this.allKey + ":\" .. KEYS[1]\n" +
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
    // region --Getter and Setter

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

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

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public int getLimitCount() {
        return limitCount;
    }

    public void setLimitCount(int limitCount) {
        this.limitCount = limitCount;
    }

    public String getAllKey() {
        return allKey;
    }

    public void setAllKey(String allKey) {
        this.allKey = allKey;
    }

    // endregion
}
