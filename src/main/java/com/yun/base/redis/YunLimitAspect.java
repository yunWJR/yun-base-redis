package com.yun.base.redis;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author: yun
 * @createdOn: 2019-05-31 16:22.
 */

@Configuration
@Aspect
@Component
public class YunLimitAspect {
    @Autowired
    private ApplicationContext ctx;

    @Pointcut(value = "@annotation(com.yun.base.redis.YunLimit)")
    public void authTokenPoint() {
    }

    @Before("@annotation(yunLimit)")
    public void beBefore(JoinPoint joinPoint, YunLimit yunLimit) throws Throwable {
        RedisLimit limit = ctx.getBean(RedisLimit.class);

        limit.checkLimit(yunLimit.key(), yunLimit.expireTime(), yunLimit.duration(), yunLimit.limitCount());
    }
}
