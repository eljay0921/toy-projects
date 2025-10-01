package com.coupon.rush.domain.coupon.entity;

import com.coupon.rush.domain.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(
    name = "coupon_issues",
    uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "coupon_id"}),
    indexes = {
        @Index(name = "idx_user_id", columnList = "user_id"),
        @Index(name = "idx_coupon_id", columnList = "coupon_id")
    }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponIssue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /// 쓰기 성능이 더 중요하므로 추가함
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /// 쓰기 성능이 더 중요하므로 추가함
    @Column(name = "coupon_id", nullable = false)
    private Long couponId;

    /// 조회용 (선택적)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    /// 조회용 (선택적)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id", insertable = false, updatable = false)
    private Coupon coupon;

    @Column(nullable = false, updatable = false)
    private LocalDateTime issuedAt;

    @Column
    private LocalDateTime usedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CouponIssueStatus status;

    public CouponIssue(User user, Coupon coupon) {
        this.user = user;
        this.coupon = coupon;
        this.issuedAt = LocalDateTime.now();
        this.status = CouponIssueStatus.ISSUED;
    }

    public CouponIssue(Long userId, Long couponId) {
        this.userId = userId;
        this.couponId = couponId;
        this.issuedAt = LocalDateTime.now();
        this.status = CouponIssueStatus.ISSUED;
    }


    public void use() {
        if (this.status != CouponIssueStatus.ISSUED) {
            throw new IllegalStateException("Coupon is not available for use");
        }
        this.status = CouponIssueStatus.USED;
        this.usedAt = LocalDateTime.now();
    }

    public void expire() {
        if (this.status == CouponIssueStatus.USED) {
            throw new IllegalStateException("Cannot expire a used coupon");
        }
        this.status = CouponIssueStatus.EXPIRED;
    }

    public enum CouponIssueStatus {
        ISSUED,
        USED,
        EXPIRED
    }
}
