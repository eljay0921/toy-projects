package com.coupon.rush.domain.coupon.service;

import com.coupon.rush.domain.coupon.dto.request.CouponCreateRequest;
import com.coupon.rush.domain.coupon.dto.response.CouponResponse;
import com.coupon.rush.domain.coupon.entity.Coupon;
import com.coupon.rush.domain.coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CouponService {

    private final CouponRepository couponRepository;

    @Transactional
    public CouponResponse createCoupon(CouponCreateRequest request) {
        validateCouponCreate(request);

        Coupon coupon = new Coupon(
            request.getCode(),
            request.getName(),
            request.getDescription(),
            request.getTotalQuantity(),
            request.getStartAt(),
            request.getEndAt()
        );

        Coupon savedCoupon = couponRepository.save(coupon);
        return CouponResponse.from(savedCoupon);
    }

    public CouponResponse getCoupon(Long id) {
        Coupon coupon = couponRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Coupon not found with id: " + id));
        return CouponResponse.from(coupon);
    }

    public CouponResponse getCouponByCode(String code) {
        Coupon coupon = couponRepository.findByCode(code)
            .orElseThrow(() -> new IllegalArgumentException("Coupon not found with code: " + code));
        return CouponResponse.from(coupon);
    }

    public List<CouponResponse> getAllCoupons() {
        return couponRepository.findAll().stream()
            .map(CouponResponse::from)
            .collect(Collectors.toList());
    }

    @Transactional
    public void deleteCoupon(Long id) {
        if (!couponRepository.existsById(id)) {
            throw new IllegalArgumentException("Coupon not found with id: " + id);
        }
        couponRepository.deleteById(id);
    }

    private void validateCouponCreate(CouponCreateRequest request) {
        if (couponRepository.existsByCode(request.getCode())) {
            throw new IllegalArgumentException("Coupon code already exists: " + request.getCode());
        }

        if (request.getStartAt().isAfter(request.getEndAt())) {
            throw new IllegalArgumentException("Start time must be before end time");
        }
    }
}
