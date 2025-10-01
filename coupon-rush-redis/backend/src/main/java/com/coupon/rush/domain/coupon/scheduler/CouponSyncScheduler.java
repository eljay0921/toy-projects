package com.coupon.rush.domain.coupon.scheduler;

import com.coupon.rush.domain.coupon.entity.CouponIssue;
import com.coupon.rush.domain.coupon.repository.CouponIssueRepository;
import com.coupon.rush.domain.coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class CouponSyncScheduler {

    private final CouponRepository couponRepository;
    private final CouponIssueRepository couponIssueRepository;
    private final StringRedisTemplate stringRedisTemplate;

    @Scheduled(fixedDelay = 60_000) // 60초
    @Transactional
    public void syncCoupons() {

        couponRepository.findAll().forEach(coupon -> {

            String issuedKey = "coupon:issued:users:" + coupon.getCode();
            Set<String> userIDs = stringRedisTemplate.opsForSet().members(issuedKey); // 해당 쿠폰을 발급한 users의 Id

            if (userIDs == null || userIDs.isEmpty()) return;

            for (String uid : userIDs) {
                Long userID = Long.valueOf(uid);
                // DB에 없는 경우에만 insert (멱등성 확보)
                try {
                    // (반영) 쿠폰 발급 정보
                    couponIssueRepository.save(new CouponIssue(userID, coupon.getId()));
                } catch (DataIntegrityViolationException e) {
                    // 중복이면 unique 제약에 의해 실패함 -> 이미 insert된 경우 무시
                }
            }

            // (반영) 최종 발급 수량
            couponRepository.findById(coupon.getId()).ifPresent(cp -> cp.updateIssuedQuantity(userIDs.size()));
        });
    }

    // TODO: redis.members() 성능 부하를 개선하기 위한 SCAN 방식 적용
}
