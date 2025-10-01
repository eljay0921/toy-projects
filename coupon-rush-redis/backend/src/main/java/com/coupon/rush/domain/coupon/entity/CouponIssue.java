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

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id", nullable = false)
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
