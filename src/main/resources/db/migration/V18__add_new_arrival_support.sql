-- ===============================================
-- Add New Arrival Support (Luxury Launch System)
-- ===============================================

ALTER TABLE products
ADD COLUMN is_new_arrival BOOLEAN NOT NULL DEFAULT FALSE;

ALTER TABLE products
ADD COLUMN launch_at TIMESTAMP WITH TIME ZONE;

ALTER TABLE products
ADD COLUMN new_arrival_position INTEGER;

-- Optional but Recommended (Hover Image Support)
ALTER TABLE product_images
ADD COLUMN is_hover_image BOOLEAN NOT NULL DEFAULT FALSE;

-- Index for performance
CREATE INDEX idx_products_new_arrival
ON products (is_new_arrival, launch_at, new_arrival_position);

CREATE INDEX idx_products_launch_at
ON products (launch_at);