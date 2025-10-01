package com.coupon.rush.domain.coupon.controller;

import com.coupon.rush.domain.coupon.dto.request.CouponCreateRequest;
import com.coupon.rush.domain.coupon.dto.request.CouponIssueRequest;
import com.coupon.rush.domain.coupon.dto.response.CouponResponse;
import com.coupon.rush.domain.coupon.service.CouponIssueService;
import com.coupon.rush.domain.coupon.service.CouponService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/coupons")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;
    private final CouponIssueService couponIssueService;

    @PostMapping
    public ResponseEntity<CouponResponse> createCoupon(@Valid @RequestBody CouponCreateRequest request) {
        CouponResponse response = couponService.createCoupon(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CouponResponse> getCoupon(@PathVariable Long id) {
        CouponResponse response = couponService.getCoupon(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<CouponResponse> getCouponByCode(@PathVariable String code) {
        CouponResponse response = couponService.getCouponByCode(code);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CouponResponse>> getAllCoupons() {
        List<CouponResponse> responses = couponService.getAllCoupons();
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoupon(@PathVariable Long id) {
        couponService.deleteCoupon(id);
        return ResponseEntity.noContent().build();
    }

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
