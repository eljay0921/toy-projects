package com.coupon.rush.domain.coupon.service;

import com.coupon.rush.domain.coupon.dto.request.CouponCreateRequest;
import com.coupon.rush.domain.coupon.dto.response.CouponResponse;
import com.coupon.rush.domain.coupon.entity.Coupon;
import com.coupon.rush.domain.coupon.repository.CouponRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CouponServiceTest {

    @Mock
    private CouponRepository couponRepository;

    @InjectMocks
    private CouponService couponService;

    private CouponCreateRequest validRequest;
    private Coupon validCoupon;

    @BeforeEach
    void setUp() {
        LocalDateTime now = LocalDateTime.now();
        validRequest = new CouponCreateRequest(
            "WELCOME2026",
            "Welcome Coupon",
            "Welcome coupon for new users",
            1000,
            now,
            now.plusDays(7)
        );

        validCoupon = new Coupon(
            validRequest.getCode(),
            validRequest.getName(),
            validRequest.getDescription(),
            validRequest.getTotalQuantity(),
            validRequest.getStartAt(),
            validRequest.getEndAt()
        );
    }

    @Test
    @DisplayName("쿠폰 생성 성공")
    void createCoupon_Success() {
        // given
        given(couponRepository.existsByCode(validRequest.getCode())).willReturn(false);
        given(couponRepository.save(any(Coupon.class))).willReturn(validCoupon);

        // when
        CouponResponse response = couponService.createCoupon(validRequest);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getCode()).isEqualTo(validRequest.getCode());
        assertThat(response.getName()).isEqualTo(validRequest.getName());
        assertThat(response.getTotalQuantity()).isEqualTo(validRequest.getTotalQuantity());
        assertThat(response.getIssuedQuantity()).isZero();

        verify(couponRepository).existsByCode(validRequest.getCode());
        verify(couponRepository).save(any(Coupon.class));
    }

    @Test
    @DisplayName("중복된 쿠폰 코드로 생성 시 예외 발생")
    void createCoupon_DuplicateCode_ThrowsException() {
        // given
        given(couponRepository.existsByCode(validRequest.getCode())).willReturn(true);

        // when & then
        assertThatThrownBy(() -> couponService.createCoupon(validRequest))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Coupon code already exists");

        verify(couponRepository).existsByCode(validRequest.getCode());
        verify(couponRepository, never()).save(any(Coupon.class));
    }

    @Test
    @DisplayName("시작 시간이 종료 시간보다 늦으면 예외 발생")
    void createCoupon_InvalidTimeRange_ThrowsException() {
        // given
        LocalDateTime now = LocalDateTime.now();
        CouponCreateRequest invalidRequest = new CouponCreateRequest(
            "INVALID",
            "Invalid Coupon",
            "Description",
            100,
            now.plusDays(7),
            now.plusHours(1)  // startAt > endAt
        );
        given(couponRepository.existsByCode(anyString())).willReturn(false);

        // when & then
        assertThatThrownBy(() -> couponService.createCoupon(invalidRequest))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Start time must be before end time");

        verify(couponRepository, never()).save(any(Coupon.class));
    }

    @Test
    @DisplayName("ID로 쿠폰 조회 성공")
    void getCoupon_Success() {
        // given
        Long couponId = 1L;
        given(couponRepository.findById(couponId)).willReturn(Optional.of(validCoupon));

        // when
        CouponResponse response = couponService.getCoupon(couponId);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getCode()).isEqualTo(validCoupon.getCode());
        verify(couponRepository).findById(couponId);
    }

    @Test
    @DisplayName("존재하지 않는 ID로 조회 시 예외 발생")
    void getCoupon_NotFound_ThrowsException() {
        // given
        Long couponId = 999L;
        given(couponRepository.findById(couponId)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> couponService.getCoupon(couponId))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Coupon not found with id");

        verify(couponRepository).findById(couponId);
    }

    @Test
    @DisplayName("코드로 쿠폰 조회 성공")
    void getCouponByCode_Success() {
        // given
        String code = "WELCOME2026";
        given(couponRepository.findByCode(code)).willReturn(Optional.of(validCoupon));

        // when
        CouponResponse response = couponService.getCouponByCode(code);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getCode()).isEqualTo(code);
        verify(couponRepository).findByCode(code);
    }

    @Test
    @DisplayName("존재하지 않는 코드로 조회 시 예외 발생")
    void getCouponByCode_NotFound_ThrowsException() {
        // given
        String code = "NOTEXIST";
        given(couponRepository.findByCode(code)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> couponService.getCouponByCode(code))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Coupon not found with code");

        verify(couponRepository).findByCode(code);
    }

    @Test
    @DisplayName("전체 쿠폰 조회 성공")
    void getAllCoupons_Success() {
        // given
        LocalDateTime now = LocalDateTime.now();
        Coupon coupon2 = new Coupon(
            "SUMMER2024",
            "Summer Sale",
            "Summer sale coupon",
            500,
            now.plusHours(1),
            now.plusDays(14)
        );
        given(couponRepository.findAll()).willReturn(List.of(validCoupon, coupon2));

        // when
        List<CouponResponse> responses = couponService.getAllCoupons();

        // then
        assertThat(responses).hasSize(2);
        assertThat(responses.get(0).getCode()).isEqualTo(validCoupon.getCode());
        assertThat(responses.get(1).getCode()).isEqualTo("SUMMER2024");
        verify(couponRepository).findAll();
    }

    @Test
    @DisplayName("쿠폰 삭제 성공")
    void deleteCoupon_Success() {
        // given
        Long couponId = 1L;
        given(couponRepository.existsById(couponId)).willReturn(true);

        // when
        couponService.deleteCoupon(couponId);

        // then
        verify(couponRepository).existsById(couponId);
        verify(couponRepository).deleteById(couponId);
    }

    @Test
    @DisplayName("존재하지 않는 쿠폰 삭제 시 예외 발생")
    void deleteCoupon_NotFound_ThrowsException() {
        // given
        Long couponId = 999L;
        given(couponRepository.existsById(couponId)).willReturn(false);

        // when & then
        assertThatThrownBy(() -> couponService.deleteCoupon(couponId))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Coupon not found with id");

        verify(couponRepository).existsById(couponId);
        verify(couponRepository, never()).deleteById(any());
    }
}
