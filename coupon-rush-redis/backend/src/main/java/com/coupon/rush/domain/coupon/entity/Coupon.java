package com.coupon.rush.domain.coupon.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "coupons")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true, length = 16)
    private String code;

    @NotBlank
    @Column(nullable = false, length = 200)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @NotNull
    @Min(1)
    @Column(nullable = false)
    private Integer totalQuantity;

    @NotNull
    @Min(0)
    @Column(nullable = false)
    private Integer issuedQuantity;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime startAt;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime endAt;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public Coupon(String code, String name, String description, Integer totalQuantity, LocalDateTime startAt, LocalDateTime endAt) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.totalQuantity = totalQuantity;
        this.issuedQuantity = 0;
        this.startAt = startAt;
        this.endAt = endAt;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isAvailable() {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(startAt) && now.isBefore(endAt) && issuedQuantity < totalQuantity;
    }

    public void incrementIssuedQuantity() {
        if (issuedQuantity >= totalQuantity) {
            throw new IllegalStateException("Coupon is sold out");
        }
        this.issuedQuantity++;
    }

    public void updateIssuedQuantity(int quantity) {
        this.issuedQuantity = quantity;
        this.updatedAt = LocalDateTime.now();
    }
}
