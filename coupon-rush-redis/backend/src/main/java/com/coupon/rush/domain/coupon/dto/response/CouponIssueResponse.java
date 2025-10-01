package com.coupon.rush.domain.coupon.dto.response;

import com.coupon.rush.domain.coupon.entity.CouponIssue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CouponIssueResponse {

    private Long id;
    private Long userId;
    private Long couponId;
    private String couponCode;
    private String couponName;
    private LocalDateTime issuedAt;
    private LocalDateTime usedAt;
    private String status;

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
