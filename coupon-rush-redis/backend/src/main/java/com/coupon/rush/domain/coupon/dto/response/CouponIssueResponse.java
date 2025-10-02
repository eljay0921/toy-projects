package com.coupon.rush.domain.coupon.dto.response;

import com.coupon.rush.domain.coupon.entity.CouponIssue;

import java.time.LocalDateTime;

public record CouponIssueResponse(
    Long id,
    Long userId,
    Long couponId,
    String couponCode,
    String couponName,
    LocalDateTime issuedAt,
    LocalDateTime usedAt,
    String status
) {
    public static CouponIssueResponse from(CouponIssue couponIssue) {
        return new CouponIssueResponse(
            couponIssue.getId(),
            couponIssue.getUserId(),
            couponIssue.getCouponId(),
            couponIssue.getCoupon() != null ? couponIssue.getCoupon().getCode() : null,
            couponIssue.getCoupon() != null ? couponIssue.getCoupon().getName() : null,
            couponIssue.getIssuedAt(),
            couponIssue.getUsedAt(),
            couponIssue.getStatus().name()
        );
    }
}
