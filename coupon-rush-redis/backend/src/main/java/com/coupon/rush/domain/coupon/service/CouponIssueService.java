package com.coupon.rush.domain.coupon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponIssueService {

    private final StringRedisTemplate redisTemplate;
    private final DefaultRedisScript<List> issueLuaScript;

    @Transactional
    public String issueCoupon(String code, Long userId) {
        String stockKey         = "coupon:stock:" + code;
        String issuedUsersKey   = "coupon:issued:users:" + code;
        String lockKey          = "coupon:user:lock:" + code + ":" + userId;

        List<String> keys = List.of(stockKey, issuedUsersKey, lockKey);
        List<String> args = List.of(userId.toString(), "3"); // lock TTL : (3) seconds

        Object res = redisTemplate.execute(issueLuaScript, keys, args.toArray());
        List<?> result = (List<?>) res;

        Long success = Long.valueOf(result.get(0).toString());
        String reason = result.get(1).toString();

        // DB에 반영해야하지만, 성능을 위해 비동기 반영을 적용 -> redis 결과만 return
        // 토이 프로젝트 수준이므로, 메시지 큐나 Outbox 패턴을 적용하기 보다는 Spring 스케줄러로 간단히 적용
        // if (success == 1) { }

        return reason;
    }
}
