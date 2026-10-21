CREATE INDEX idx_products_category_active
ON products(category_id, is_active);

CREATE INDEX idx_categories_slug
ON categories(slug);

CREATE INDEX idx_variants_product_default
ON product_variants(product_id, is_default);

CREATE INDEX idx_inventory_variant
ON inventory(variant_id);