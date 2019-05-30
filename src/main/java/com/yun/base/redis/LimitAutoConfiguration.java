package com.yun.base.redis;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author: yun
 * @createdOn: 2019-05-30 13:05.
 */

@Configuration
@EnableConfigurationProperties(RedisLimitProperties.class)
@Import({RedisLimitAutoConfiguration.class})
public class LimitAutoConfiguration {
}
