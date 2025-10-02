-- Users table
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

-- Coupons table
CREATE TABLE coupons (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(16) NOT NULL UNIQUE,
    name VARCHAR(200) NOT NULL,
    description TEXT,
    total_quantity INTEGER NOT NULL CHECK (total_quantity >= 1),
    issued_quantity INTEGER NOT NULL CHECK (issued_quantity >= 0),
    start_at TIMESTAMP NOT NULL,
    end_at TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

-- Coupon Issues table
CREATE TABLE coupon_issues (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    coupon_id BIGINT NOT NULL,
    issued_at TIMESTAMP NOT NULL,
    used_at TIMESTAMP,
    status VARCHAR(20) NOT NULL,
    CONSTRAINT fk_coupon_issues_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_coupon_issues_coupon FOREIGN KEY (coupon_id) REFERENCES coupons(id),
    CONSTRAINT uk_user_coupon UNIQUE (user_id, coupon_id)
);

-- Indexes for coupon_issues
CREATE INDEX idx_user_id ON coupon_issues(user_id);
CREATE INDEX idx_coupon_id ON coupon_issues(coupon_id);
