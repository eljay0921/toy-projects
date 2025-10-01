package com.coupon.rush.domain.coupon.repository;

import com.coupon.rush.domain.coupon.entity.CouponIssue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CouponIssueRepository extends JpaRepository<CouponIssue, Long> {
    boolean existsByUserIdAndCouponId(Long userId, Long couponId);
    Optional<CouponIssue> findByUserIdAndCouponId(Long userId, Long couponId);
    List<CouponIssue> findAllByUserId(Long userId);
    List<CouponIssue> findAllByCouponId(Long couponId);
    long countByCouponId(Long couponId);
}
