package com.yun.base.redis;

import java.lang.annotation.*;

/**
 * @author: yun
 * @createdOn: 2019-05-31 16:16.
 */

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface YunLimit {
    /**
     * key
     */
    String key() default "";

    /**
     * 过期时间 s
     */
    int expireTime() default 1;

    /**
     * 限制时间 s
     */
    int duration() default 1;

    /**
     * 限制次数
     */
    int limitCount() default 100;
}
