-- ============================================================
-- V2__create_website_media.sql
-- Centralized website-managed media registry
-- Supports Hero, banners, videos, promos, etc.
-- ============================================================

CREATE TABLE website_media (

    website_media_id        UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    -- What kind of media this is
    -- IMAGE | VIDEO | BACKGROUND_IMAGE | PROMO
    website_media_type      VARCHAR(50) NOT NULL,

    -- Where this media is used
    -- HOME_HERO | HOME_BANNER | PRODUCT_GALLERY | ABOUT_BANNER
    page_section            VARCHAR(100) NOT NULL,

    -- Textual content (used mainly for Hero / banners)
    title                   VARCHAR(255) NOT NULL,   -- Hero headline
    subtitle                TEXT,                     -- Hero subtext (optional)

    -- Cloudinary URL (image or video)
    website_media_url       TEXT NOT NULL,

    -- Accessibility / SEO
    alt_text                VARCHAR(255),

    -- Display order within a section
    position                INT NOT NULL,

    -- Soft enable/disable
    is_active               BOOLEAN NOT NULL DEFAULT true,

    -- Audit
    created_at              TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at              TIMESTAMPTZ NOT NULL DEFAULT now()
);

-- ============================================================
-- Indexes for fast homepage & section loading
-- ============================================================

CREATE INDEX idx_website_media_section_active
ON website_media (page_section, is_active);

CREATE INDEX idx_website_media_section_position
ON website_media (page_section, position);
