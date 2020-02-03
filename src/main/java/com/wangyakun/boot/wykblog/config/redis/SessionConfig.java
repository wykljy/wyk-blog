package com.wangyakun.boot.wykblog.config.redis;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @ClassName SessionConfig
 * @Description TODO
 * @Author wangyakun
 * @Date 2020/2/3 22:21
 * @Version 1.0
 **/
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 86400*30)
public class SessionConfig {
}
