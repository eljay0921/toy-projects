package com.coupon.rush.domain.coupon.dto.response;

import com.coupon.rush.domain.coupon.entity.Coupon;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CouponResponse {

    private Long id;
    private String code;
    private String name;
    private String description;
    private Integer totalQuantity;
    private Integer issuedQuantity;
    private Integer remainingQuantity;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private boolean available;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CouponResponse from(Coupon coupon) {
        return new CouponResponse(
            coupon.getId(),
            coupon.getCode(),
            coupon.getName(),
            coupon.getDescription(),
            coupon.getTotalQuantity(),
            coupon.getIssuedQuantity(),
            coupon.getRemainingQuantity(),
            coupon.getStartAt(),
            coupon.getEndAt(),
            coupon.isAvailable(),
            coupon.getCreatedAt(),
            coupon.getUpdatedAt()
        );
    }
}
