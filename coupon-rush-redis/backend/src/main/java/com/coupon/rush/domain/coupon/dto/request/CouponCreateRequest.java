package com.coupon.rush.domain.coupon.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public record CouponCreateRequest(

        @NotBlank(message = "Coupon code is required")
        String code,

        @NotBlank(message = "Coupon name is required")
        String name,

        String description,

        @NotNull(message = "Total quantity is required")
        @Min(value = 1, message = "Total quantity must be at least 1")
        Integer totalQuantity,

        @NotNull(message = "Start time is required")
        LocalDateTime startAt,

        @NotNull(message = "End time is required")
        LocalDateTime endAt
) {}