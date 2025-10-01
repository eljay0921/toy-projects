package com.coupon.rush.infrastructure.redis.script;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.List;

@Configuration
public class RedisScriptConfig {

    @Bean
    public DefaultRedisScript<List> issueLuaScript() {
        DefaultRedisScript<List> script = new DefaultRedisScript<>();
        script.setLocation(new ClassPathResource("issue.lua"));
        script.setResultType(List.class);
        return script;
    }
}
