CREATE UNIQUE INDEX uq_product_default_variant
ON product_variants (product_id)
WHERE is_default = TRUE;
