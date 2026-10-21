ALTER TABLE product_variants
ADD COLUMN is_default BOOLEAN NOT NULL DEFAULT FALSE;


UPDATE product_variants pv
SET is_default = true
WHERE pv.variant_id = (
    SELECT pv2.variant_id
    FROM product_variants pv2
    WHERE pv2.product_id = pv.product_id
    ORDER BY pv2.created_at
    LIMIT 1
);
