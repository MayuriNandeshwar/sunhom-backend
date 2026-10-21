-- ===============================
-- ORDER ADDRESSES
-- ===============================
CREATE TABLE IF NOT EXISTS order_addresses (
    order_address_id UUID PRIMARY KEY,
    order_id UUID NOT NULL,

    full_name VARCHAR(100) NOT NULL,
    phone VARCHAR(15) NOT NULL,
    address_line1 VARCHAR(255) NOT NULL,
    address_line2 VARCHAR(255),
    city VARCHAR(100) NOT NULL,
    state VARCHAR(100) NOT NULL,
    pincode VARCHAR(20) NOT NULL,
    country VARCHAR(100) DEFAULT 'India',

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_order_address_order
        FOREIGN KEY (order_id) REFERENCES orders(order_id)
);

-- ===============================
-- CUSTOMER PROFILE
-- ===============================
CREATE TABLE IF NOT EXISTS customer_profile (
    user_id UUID PRIMARY KEY,

    total_orders INT DEFAULT 0,
    total_spent DECIMAL(12,2) DEFAULT 0,
    last_order_at TIMESTAMP,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,

    CONSTRAINT fk_customer_profile_user
        FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- ===============================
-- ORDER STATUS HISTORY
-- ===============================
CREATE TABLE IF NOT EXISTS order_status_history (
    history_id UUID PRIMARY KEY,
    order_id UUID NOT NULL,

    status VARCHAR(30) NOT NULL,
    note VARCHAR(255),

    changed_by UUID,
    changed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_order_status_order
        FOREIGN KEY (order_id) REFERENCES orders(order_id)
);

-- ===============================
-- PRODUCT STOCK LOG
-- ===============================
CREATE TABLE IF NOT EXISTS product_stock_log (
    log_id UUID PRIMARY KEY,
    variant_id UUID NOT NULL,

    change_type VARCHAR(30) NOT NULL,
    change_amount INT NOT NULL,
    note VARCHAR(255),

    changed_by UUID,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_stock_variant
        FOREIGN KEY (variant_id) REFERENCES product_variants(variant_id),

    CONSTRAINT fk_stock_changed_by
        FOREIGN KEY (changed_by) REFERENCES users(user_id)
);

-- ===============================
-- WISHLISTS
-- ===============================
CREATE TABLE IF NOT EXISTS wishlists (
    wishlist_id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    product_id UUID NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT uq_wishlist_user_product
        UNIQUE (user_id, product_id),

    CONSTRAINT fk_wishlist_user
        FOREIGN KEY (user_id) REFERENCES users(user_id),

    CONSTRAINT fk_wishlist_product
        FOREIGN KEY (product_id) REFERENCES products(product_id)
);

-- ===============================
-- ACTIVITY LOGS
-- ===============================
CREATE TABLE IF NOT EXISTS activity_logs (
    log_id UUID PRIMARY KEY,
    user_id UUID,
    action VARCHAR(50) NOT NULL,
    entity_type VARCHAR(50),
    entity_id UUID,
    metadata JSONB,
    ip_address VARCHAR(45),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
