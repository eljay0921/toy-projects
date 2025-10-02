package com.coupon.rush.domain.coupon.dto.response;

import com.coupon.rush.domain.coupon.entity.Coupon;

import java.time.LocalDateTime;

public record CouponResponse(
    Long id,
    String code,
    String name,
    String description,
    Integer totalQuantity,
    Integer issuedQuantity,
    LocalDateTime startAt,
    LocalDateTime endAt,
    boolean available,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    public static CouponResponse from(Coupon coupon) {
        return new CouponResponse(
            coupon.getId(),
            coupon.getCode(),
            coupon.getName(),
            coupon.getDescription(),
            coupon.getTotalQuantity(),
            coupon.getIssuedQuantity(),
            coupon.getStartAt(),
            coupon.getEndAt(),
            coupon.isAvailable(),
            coupon.getCreatedAt(),
            coupon.getUpdatedAt()
        );
    }
}
