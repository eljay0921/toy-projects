package com.coupon.rush.domain.coupon.controller;

import com.coupon.rush.domain.coupon.dto.request.CouponIssueRequest;
import com.coupon.rush.domain.coupon.service.CouponIssueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/coupons")
@RequiredArgsConstructor
public class CouponController {

    private final CouponIssueService couponIssueService;

    @PostMapping("/issue")
    public ResponseEntity<?> issueCoupon(@RequestBody @Valid CouponIssueRequest request) {
        String result = couponIssueService.issueCoupon(request.code(), request.userID());

        // TODO: return 포맷 통합
        return ResponseEntity.ok(Map.of(
                "code", request.code(),
                "userID", request.userID(),
                "status", result
        ));
    }
}
