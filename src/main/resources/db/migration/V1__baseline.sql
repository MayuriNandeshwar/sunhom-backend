-- ============================================================
-- V1__baseline.sql
-- Initial database schema for Chamakk E-commerce Platform
-- DO NOT MODIFY AFTER CREATION
-- ============================================================

-- =========================
-- EXTENSIONS
-- =========================
CREATE EXTENSION IF NOT EXISTS pgcrypto WITH SCHEMA public;

-- =========================
-- FUNCTIONS
-- =========================
CREATE FUNCTION public.products_search_vector_trigger() RETURNS trigger
LANGUAGE plpgsql
AS $$
BEGIN
    NEW.search_vector :=
        setweight(to_tsvector('simple', coalesce(NEW.product_name, '')), 'A') ||
        setweight(to_tsvector('simple', coalesce(NEW.short_description, '')), 'B') ||
        setweight(to_tsvector('simple', coalesce(NEW.product_description, '')), 'C') ||
        setweight(to_tsvector('simple', coalesce(NEW.search_keywords, '')), 'D');
    RETURN NEW;
END;
$$;

-- =========================
-- AUTH & USER
-- =========================

CREATE TABLE public.roles (
    role_id uuid NOT NULL DEFAULT gen_random_uuid(),
    role_name varchar(100) NOT NULL,
    description varchar(255),
    created_at timestamptz NOT NULL DEFAULT now(),

    -- Primary Key
    CONSTRAINT roles_pkey PRIMARY KEY (role_id),
    -- Unique role name
    CONSTRAINT roles_role_name_key UNIQUE (role_name),
    -- Ensure role_name is not empty or whitespace
    CONSTRAINT roles_role_name_not_blank CHECK (btrim(role_name) <> '')
);

CREATE TABLE public.permissions (
    permission_id uuid NOT NULL DEFAULT gen_random_uuid(),
    permission_code varchar(100) NOT NULL,
    description varchar(255),
    is_active boolean NOT NULL DEFAULT true,
    created_at timestamptz NOT NULL DEFAULT now(),
    updated_at timestamptz NOT NULL DEFAULT now(),

    -- Primary Key
    CONSTRAINT permissions_pkey PRIMARY KEY (permission_id),
    -- Unique permission code
    CONSTRAINT permissions_permission_code_key UNIQUE (permission_code),
    -- Ensure permission_code is not empty or whitespace
    CONSTRAINT permissions_permission_code_not_blank
        CHECK (btrim(permission_code) <> '')
);

CREATE TABLE public.users (
    user_id uuid NOT NULL DEFAULT gen_random_uuid(),
    email varchar(255),
    mobile varchar(20),
    password_hash text NOT NULL,
    is_active boolean NOT NULL DEFAULT true,
    created_at timestamptz NOT NULL DEFAULT now(),
    updated_at timestamptz NOT NULL DEFAULT now(),

    CONSTRAINT users_pkey PRIMARY KEY (user_id),
    CONSTRAINT uq_users_email UNIQUE (email),
    CONSTRAINT uq_users_mobile UNIQUE (mobile),

    CONSTRAINT users_email_or_mobile_required CHECK (
        email IS NOT NULL OR mobile IS NOT NULL
    )
);


CREATE TABLE public.user_roles (
    user_role_id uuid NOT NULL DEFAULT gen_random_uuid(),
    user_id uuid NOT NULL,
    role_id uuid NOT NULL,
    assigned_at timestamptz NOT NULL DEFAULT now(),

    CONSTRAINT user_roles_pkey PRIMARY KEY (user_role_id),
    CONSTRAINT uq_user_role UNIQUE (user_id, role_id),

    CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id)
        REFERENCES public.users(user_id) ON DELETE CASCADE,

    CONSTRAINT fk_user_roles_role FOREIGN KEY (role_id)
        REFERENCES public.roles(role_id) ON DELETE CASCADE
);


CREATE TABLE public.role_permissions (
    role_permission_id uuid NOT NULL DEFAULT gen_random_uuid(),
    role_id uuid NOT NULL,
    permission_id uuid NOT NULL,
    created_at timestamptz NOT NULL DEFAULT now(),

    -- Primary Key
    CONSTRAINT role_permissions_pkey PRIMARY KEY (role_permission_id),

    -- Foreign Keys
    CONSTRAINT fk_role FOREIGN KEY (role_id)
        REFERENCES public.roles(role_id) ON DELETE CASCADE,
    CONSTRAINT fk_permission FOREIGN KEY (permission_id)
        REFERENCES public.permissions(permission_id) ON DELETE CASCADE,

    -- Prevent duplicate permission assignment to same role
    CONSTRAINT uq_role_permission UNIQUE (role_id, permission_id)
);

CREATE TABLE public.user_sessions (
    session_id uuid NOT NULL DEFAULT gen_random_uuid(),
    user_id uuid NOT NULL,
    refresh_token text NOT NULL,
    device_id text NOT NULL,
    device_type varchar(20),
    ip_address inet,
    user_agent text,
    status varchar(20) NOT NULL,
    created_at timestamptz NOT NULL DEFAULT now(),
    last_used_at timestamptz,
    expires_at timestamptz NOT NULL,
    revoked_reason text,

    -- Primary Key
    CONSTRAINT user_sessions_pkey PRIMARY KEY (session_id),

    -- Unique refresh token
    CONSTRAINT user_sessions_refresh_token_key UNIQUE (refresh_token),

    -- Status validation
    CONSTRAINT user_sessions_status_check
        CHECK (status IN ('ACTIVE', 'REVOKED', 'EXPIRED')),

    -- Foreign Key
    CONSTRAINT fk_user_sessions_user
        FOREIGN KEY (user_id)
        REFERENCES public.users(user_id)
        ON DELETE CASCADE
);

CREATE TABLE public.failed_login_attempts (
    attempt_id uuid NOT NULL DEFAULT gen_random_uuid(),
    email varchar(255),
    mobile varchar(20),
    ip_address inet,
    attempted_at timestamptz NOT NULL DEFAULT now(),

    CONSTRAINT failed_login_attempts_pkey PRIMARY KEY (attempt_id)
);

CREATE TABLE public.password_reset_tokens (
    reset_id uuid NOT NULL DEFAULT gen_random_uuid(),
    user_id uuid NOT NULL,
    token text NOT NULL,
    expires_at timestamptz NOT NULL,
    used boolean NOT NULL DEFAULT false,
    created_at timestamptz NOT NULL DEFAULT now(),

    CONSTRAINT password_reset_tokens_pkey PRIMARY KEY (reset_id),
    CONSTRAINT password_reset_tokens_token_key UNIQUE (token),

    CONSTRAINT fk_reset_user FOREIGN KEY (user_id)
        REFERENCES public.users(user_id) ON DELETE CASCADE
);


-- =========================
-- CATALOG
-- =========================
CREATE TABLE public.categories (
    category_id uuid NOT NULL DEFAULT gen_random_uuid(),
    category_name varchar(255) NOT NULL,
    slug varchar(255) NOT NULL,
    category_description text,
    image_url text,
    is_active boolean NOT NULL DEFAULT true,
    created_at timestamptz NOT NULL DEFAULT now(),
    updated_at timestamptz NOT NULL DEFAULT now(),
    deleted_at timestamptz,
    parent_id uuid,

    CONSTRAINT categories_pkey PRIMARY KEY (category_id),
    CONSTRAINT categories_slug_key UNIQUE (slug),

    CONSTRAINT fk_categories_parent FOREIGN KEY (parent_id)
        REFERENCES public.categories(category_id) ON DELETE SET NULL
);

CREATE TABLE public.product_types (
    product_type_id uuid NOT NULL DEFAULT gen_random_uuid(),
    product_type_name varchar(255) NOT NULL,
    product_type_description text,
    created_at timestamptz NOT NULL DEFAULT now(),
    updated_at timestamptz NOT NULL DEFAULT now(),

    CONSTRAINT product_types_pkey PRIMARY KEY (product_type_id),
    CONSTRAINT product_types_product_type_name_key UNIQUE (product_type_name)
);
CREATE TABLE public.products (
    product_id uuid NOT NULL DEFAULT gen_random_uuid(),
    sku_base varchar(100),
    product_name varchar(255) NOT NULL,
    slug varchar(255) NOT NULL,
    short_description text,
    product_description text,
    brand varchar(255),
    search_keywords text,
    is_active boolean NOT NULL DEFAULT true,
    is_featured boolean NOT NULL DEFAULT false,
    is_manual_bestseller boolean NOT NULL DEFAULT false,
    created_at timestamptz NOT NULL DEFAULT now(),
    updated_at timestamptz NOT NULL DEFAULT now(),
    deleted_at timestamptz,

    created_by uuid,
    updated_by uuid,

    search_vector tsvector,
    product_type_id uuid NOT NULL,
    category_id uuid NOT NULL,
    seo_title varchar(255),
    seo_description text,
    seo_keywords text,

    CONSTRAINT products_pkey PRIMARY KEY (product_id),
    CONSTRAINT products_slug_key UNIQUE (slug),

    CONSTRAINT fk_product_types
        FOREIGN KEY (product_type_id)
        REFERENCES public.product_types(product_type_id),

    CONSTRAINT fk_products_category
        FOREIGN KEY (category_id)
        REFERENCES public.categories(category_id)
);

COMMENT ON COLUMN public.products.created_by
IS 'Logical reference to users.user_id (not enforced at DB level)';

COMMENT ON COLUMN public.products.updated_by
IS 'Logical reference to users.user_id (not enforced at DB level)';


CREATE TABLE public.product_variants (
    variant_id uuid NOT NULL DEFAULT gen_random_uuid(),
    product_id uuid NOT NULL,
    sku varchar(100) NOT NULL,
    variant_title varchar(255),
    fragrance varchar(255),
    weight_grams integer,
    size_label varchar(50),
    color varchar(50),
    burn_time_hours integer,
    price numeric(10,2) NOT NULL,
    mrp numeric(10,2),
    is_active boolean NOT NULL DEFAULT true,
    created_at timestamptz NOT NULL DEFAULT now(),
    updated_at timestamptz NOT NULL DEFAULT now(),
    deleted_at timestamptz,
    created_by uuid,
    updated_by uuid,

    CONSTRAINT product_variants_pkey PRIMARY KEY (variant_id),
    CONSTRAINT product_variants_sku_key UNIQUE (sku),

    CONSTRAINT fk_products FOREIGN KEY (product_id)
        REFERENCES public.products(product_id) ON DELETE CASCADE
);

CREATE TABLE public.product_images (
    product_image_id uuid NOT NULL DEFAULT gen_random_uuid(),
    product_id uuid NOT NULL,
    variant_id uuid,
    product_image_url text NOT NULL,
    alt_text varchar(255),
    position integer,
    media_type varchar(50),
    is_primary boolean NOT NULL DEFAULT false,
    created_at timestamptz NOT NULL DEFAULT now(),

    CONSTRAINT product_images_pkey PRIMARY KEY (product_image_id),

    CONSTRAINT fk_products FOREIGN KEY (product_id)
        REFERENCES public.products(product_id) ON DELETE CASCADE,

    CONSTRAINT fk_product_variants FOREIGN KEY (variant_id)
        REFERENCES public.product_variants(variant_id) ON DELETE SET NULL

);

CREATE TABLE public.inventory (
    inventory_id uuid NOT NULL DEFAULT gen_random_uuid(),
    variant_id uuid NOT NULL,
    available_quantity integer NOT NULL DEFAULT 0,
    reserved_quantity integer NOT NULL DEFAULT 0,
    low_stock_threshold integer NOT NULL DEFAULT 0,
    updated_at timestamptz NOT NULL DEFAULT now(),

    CONSTRAINT inventory_pkey PRIMARY KEY (inventory_id),
    CONSTRAINT inventory_variant_id_key UNIQUE (variant_id),
    CONSTRAINT fk_inventory_variant FOREIGN KEY (variant_id)
        REFERENCES public.product_variants(variant_id) ON DELETE CASCADE
);

-- =========================
-- CART & ORDERS
-- =========================

CREATE TABLE public.carts (
    cart_id uuid NOT NULL DEFAULT gen_random_uuid(),
    user_id uuid NOT NULL,
    status varchar(50) NOT NULL DEFAULT 'ACTIVE',
    created_at timestamptz NOT NULL DEFAULT now(),
    updated_at timestamptz NOT NULL DEFAULT now(),

    CONSTRAINT carts_pkey PRIMARY KEY (cart_id),
    CONSTRAINT carts_status_check CHECK (
        status IN ('ACTIVE', 'CHECKED_OUT', 'ABANDONED')
    ),

    CONSTRAINT fk_cart_user FOREIGN KEY (user_id)
        REFERENCES public.users(user_id) ON DELETE CASCADE
);


CREATE TABLE public.cart_items (
    cart_item_id uuid NOT NULL DEFAULT gen_random_uuid(),
    cart_id uuid NOT NULL,
    variant_id uuid NOT NULL,
    quantity integer NOT NULL,
    price_at_add numeric(10,2) NOT NULL,
    added_at timestamptz NOT NULL DEFAULT now(),

    CONSTRAINT cart_items_pkey PRIMARY KEY (cart_item_id),
    CONSTRAINT cart_items_cart_id_variant_id_key UNIQUE (cart_id, variant_id),
    CONSTRAINT cart_items_quantity_check CHECK (quantity > 0),

    CONSTRAINT fk_cart_item_cart FOREIGN KEY (cart_id)
        REFERENCES public.carts(cart_id) ON DELETE CASCADE,
    CONSTRAINT fk_cart_item_variant FOREIGN KEY (variant_id)
        REFERENCES public.product_variants(variant_id)
);

CREATE TABLE public.orders (
    order_id uuid NOT NULL DEFAULT gen_random_uuid(),
    order_number varchar(50) NOT NULL,
    user_id uuid NOT NULL,
    status varchar(50) NOT NULL,
    payment_status varchar(50) NOT NULL,
    subtotal numeric(10,2) NOT NULL,
    discount_amount numeric(10,2) DEFAULT 0,
    tax_amount numeric(10,2) DEFAULT 0,
    shipping_amount numeric(10,2) DEFAULT 0,
    total_amount numeric(10,2) NOT NULL,
    payment_attempts integer NOT NULL DEFAULT 0,
    placed_at timestamptz,
    cancelled_at timestamptz,
    created_at timestamptz NOT NULL DEFAULT now(),
    updated_at timestamptz NOT NULL DEFAULT now(),

    CONSTRAINT orders_pkey PRIMARY KEY (order_id),
    CONSTRAINT orders_order_number_key UNIQUE (order_number),
    CONSTRAINT orders_order_number_format_check CHECK (order_number ~ '^[A-Z0-9\\-]+$'),
    CONSTRAINT orders_status_check CHECK (
        status IN (
            'CREATED',
            'PLACED',
            'CONFIRMED',
            'SHIPPED',
            'DELIVERED',
            'CANCELLED'
        )
    ),

    CONSTRAINT orders_payment_status_check CHECK (
        payment_status IN (
            'PENDING',
            'PAID',
            'FAILED',
            'REFUNDED'
        )
    ),

    CONSTRAINT fk_orders_user FOREIGN KEY (user_id)
        REFERENCES public.users(user_id) ON DELETE RESTRICT
);

CREATE TABLE public.order_items (
    order_item_id uuid NOT NULL DEFAULT gen_random_uuid(),
    order_id uuid NOT NULL,
    variant_id uuid,
    product_name varchar(255) NOT NULL,
    variant_title varchar(255),
    quantity integer NOT NULL,
    unit_price numeric(10,2) NOT NULL,
    total_price numeric(10,2) NOT NULL,
    created_at timestamptz NOT NULL DEFAULT now(),

    CONSTRAINT order_items_pkey PRIMARY KEY (order_item_id),
    CONSTRAINT order_items_quantity_check CHECK (quantity > 0),

    CONSTRAINT fk_order_items_order FOREIGN KEY (order_id)
        REFERENCES public.orders(order_id) ON DELETE CASCADE,

    CONSTRAINT fk_order_items_variant FOREIGN KEY (variant_id)
        REFERENCES public.product_variants(variant_id)
        ON DELETE SET NULL
);


-- =========================
-- INDEXES
-- =========================
CREATE INDEX idx_categories_active ON public.categories (is_active);
CREATE INDEX idx_categories_name ON public.categories (category_name);
CREATE INDEX idx_categories_parent ON public.categories (parent_id);

CREATE INDEX idx_products_active ON public.products (is_active);
CREATE INDEX idx_products_category ON public.products (category_id);
CREATE INDEX idx_products_created ON public.products (created_at);
CREATE INDEX idx_products_name ON public.products (product_name);

CREATE INDEX idx_products_type ON public.products (product_type_id);
CREATE INDEX idx_products_search_vector ON public.products USING GIN (search_vector);

CREATE INDEX idx_variants_active ON public.product_variants (is_active);
CREATE INDEX idx_variants_price ON public.product_variants (price);
CREATE INDEX idx_variants_product ON public.product_variants (product_id);

CREATE INDEX idx_product_images_primary ON public.product_images (is_primary);
CREATE INDEX idx_product_images_product ON public.product_images (product_id);
CREATE INDEX idx_product_images_variant ON public.product_images (variant_id);

CREATE INDEX idx_inventory_low_stock_alert ON public.inventory (available_quantity)
WHERE available_quantity <= low_stock_threshold;


CREATE INDEX idx_users_active ON public.users (is_active);
CREATE INDEX idx_users_mobile ON public.users (mobile);

CREATE INDEX idx_user_sessions_user ON public.user_sessions (user_id);
CREATE INDEX idx_user_sessions_status ON public.user_sessions (status);
CREATE INDEX idx_user_sessions_expires ON public.user_sessions (expires_at);

CREATE INDEX idx_failed_login_email ON public.failed_login_attempts (email);
CREATE INDEX idx_failed_login_ip ON public.failed_login_attempts (ip_address);

CREATE UNIQUE INDEX uq_cart_user_active ON public.carts (user_id) WHERE status = 'ACTIVE';
CREATE INDEX idx_carts_updated ON public.carts (updated_at);

CREATE INDEX idx_cart_items_cart ON public.cart_items (cart_id);

CREATE INDEX idx_orders_status ON public.orders (status);
CREATE INDEX idx_orders_user ON public.orders (user_id);

CREATE INDEX idx_order_items_order ON public.order_items (order_id);

-- =========================
-- TRIGGERS
-- =========================
CREATE TRIGGER trg_products_search_vector
BEFORE INSERT OR UPDATE ON public.products
FOR EACH ROW
EXECUTE FUNCTION public.products_search_vector_trigger();
