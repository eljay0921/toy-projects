package com.coupon.rush.domain.coupon.controller;

import com.coupon.rush.domain.coupon.dto.request.CouponCreateRequest;
import com.coupon.rush.domain.coupon.dto.response.CouponResponse;
import com.coupon.rush.domain.coupon.service.CouponIssueService;
import com.coupon.rush.domain.coupon.service.CouponService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CouponController.class, properties = {
    "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration"
})
class CouponControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CouponService couponService;

    @MockitoBean
    private CouponIssueService couponIssueService;

    private ObjectMapper objectMapper;
    private CouponCreateRequest validRequest;
    private CouponResponse validResponse;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        LocalDateTime now = LocalDateTime.now();
        validRequest = new CouponCreateRequest(
            "WELCOME2026",
            "Welcome Coupon",
            "Welcome coupon for new users",
            1000,
            now,
            now.plusDays(7)
        );

        validResponse = new CouponResponse(
            1L,
            "WELCOME2026",
            "Welcome Coupon",
            "Welcome coupon for new users",
            1000,
            0,
            now,
            now.plusDays(7),
            true,
            now,
            now
        );
    }

    @Test
    @DisplayName("POST /api/coupons - 쿠폰 생성 성공")
    void createCoupon_Success() throws Exception {
        // given
        given(couponService.createCoupon(any(CouponCreateRequest.class))).willReturn(validResponse);

        // when & then
        mockMvc.perform(post("/api/coupons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.code").value("WELCOME2026"))
            .andExpect(jsonPath("$.name").value("Welcome Coupon"))
            .andExpect(jsonPath("$.totalQuantity").value(1000))
            .andExpect(jsonPath("$.issuedQuantity").value(0));
    }

    @Test
    @DisplayName("POST /api/coupons - 유효성 검증 실패 (빈 코드)")
    void createCoupon_ValidationFail_EmptyCode() throws Exception {
        // given
        CouponCreateRequest invalidRequest = new CouponCreateRequest(
            "",
            "Welcome Coupon",
            "Description",
            1000,
            LocalDateTime.now().plusHours(1),
            LocalDateTime.now().plusDays(7)
        );

        // when & then
        mockMvc.perform(post("/api/coupons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/coupons - 유효성 검증 실패 (수량 0)")
    void createCoupon_ValidationFail_InvalidQuantity() throws Exception {
        // given
        CouponCreateRequest invalidRequest = new CouponCreateRequest(
            "CODE",
            "Name",
            "Description",
            0,  // invalid
            LocalDateTime.now().plusHours(1),
            LocalDateTime.now().plusDays(7)
        );

        // when & then
        mockMvc.perform(post("/api/coupons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /api/coupons/{id} - ID로 쿠폰 조회 성공")
    void getCoupon_Success() throws Exception {
        // given
        given(couponService.getCoupon(1L)).willReturn(validResponse);

        // when & then
        mockMvc.perform(get("/api/coupons/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.code").value("WELCOME2026"));
    }

    @Test
    @DisplayName("GET /api/coupons/{id} - 존재하지 않는 쿠폰 조회")
    void getCoupon_NotFound() throws Exception {
        // given
        given(couponService.getCoupon(999L))
            .willThrow(new IllegalArgumentException("Coupon not found with id: 999"));

        // when & then
        mockMvc.perform(get("/api/coupons/999"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("Coupon not found with id: 999"));
    }

    @Test
    @DisplayName("GET /api/coupons/code/{code} - 코드로 쿠폰 조회 성공")
    void getCouponByCode_Success() throws Exception {
        // given
        given(couponService.getCouponByCode("WELCOME2026")).willReturn(validResponse);

        // when & then
        mockMvc.perform(get("/api/coupons/code/WELCOME2026"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("WELCOME2026"))
            .andExpect(jsonPath("$.name").value("Welcome Coupon"));
    }

    @Test
    @DisplayName("GET /api/coupons/code/{code} - 존재하지 않는 코드 조회")
    void getCouponByCode_NotFound() throws Exception {
        // given
        given(couponService.getCouponByCode("NOTEXIST"))
            .willThrow(new IllegalArgumentException("Coupon not found with code: NOTEXIST"));

        // when & then
        mockMvc.perform(get("/api/coupons/code/NOTEXIST"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("Coupon not found with code: NOTEXIST"));
    }

    @Test
    @DisplayName("GET /api/coupons - 전체 쿠폰 조회 성공")
    void getAllCoupons_Success() throws Exception {
        // given
        LocalDateTime now = LocalDateTime.now();
        CouponResponse response2 = new CouponResponse(
            2L, "SUMMER2024", "Summer Sale", "Description",
            500, 100, now, now.plusDays(14), true, now, now
        );
        given(couponService.getAllCoupons()).willReturn(List.of(validResponse, response2));

        // when & then
        mockMvc.perform(get("/api/coupons"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].code").value("WELCOME2026"))
            .andExpect(jsonPath("$[1].code").value("SUMMER2024"));
    }

    @Test
    @DisplayName("DELETE /api/coupons/{id} - 쿠폰 삭제 성공")
    void deleteCoupon_Success() throws Exception {
        // given
        doNothing().when(couponService).deleteCoupon(1L);

        // when & then
        mockMvc.perform(delete("/api/coupons/1"))
            .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /api/coupons/{id} - 존재하지 않는 쿠폰 삭제")
    void deleteCoupon_NotFound() throws Exception {
        // given
        doThrow(new IllegalArgumentException("Coupon not found with id: 999"))
            .when(couponService).deleteCoupon(999L);

        // when & then
        mockMvc.perform(delete("/api/coupons/999"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("Coupon not found with id: 999"));
    }
}
