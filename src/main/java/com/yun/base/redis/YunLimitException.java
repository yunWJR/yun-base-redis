package com.yun.base.redis;

/**
 * @author: yun
 * @createdOn: 2019-05-31 16:35.
 */

public class YunLimitException extends RuntimeException {
    private RedisLimitPara para;
    private boolean isSuc = false;

    public YunLimitException() {

    }

    public YunLimitException(RedisLimitPara para) {
        this.para = para;
    }
}