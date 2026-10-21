INSERT INTO inventory
(
variant_id,
available_quantity,
reserved_quantity,
low_stock_threshold,
updated_at
)

SELECT
v.variant_id,
100,
0,
10,
NOW()
FROM product_variants v
WHERE v.sku IN
(
'SUN-CIN-220',
'SUN-COF-220',
'SUN-LAV-220',
'SUN-LEM-220',
'SUN-MAH-220'
);