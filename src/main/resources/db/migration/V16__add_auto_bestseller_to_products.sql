ALTER TABLE products
ADD COLUMN is_auto_bestseller BOOLEAN NOT NULL DEFAULT FALSE;

CREATE INDEX idx_products_auto_bestseller
ON products(is_auto_bestseller);
