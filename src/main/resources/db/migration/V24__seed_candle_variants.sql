INSERT INTO product_variants
(
product_id,
sku,
variant_title,
fragrance,
weight_grams,
size_label,
color,
duration_hours,
price,
mrp,
is_active,
created_at,
updated_at,
created_by,
updated_by,
is_default
)

SELECT
p.product_id,
'SUN-CIN-220',
'220g Candle',
'Cinnamon Vanilla',
220,
'3" × 3.5" × 3.5"',
'white',
45,
599,
699,
true,
now(),
now(),
NULL::uuid,
NULL::uuid,
true
FROM products p
WHERE p.slug='cinnamon-vanilla-soy-candle'

UNION ALL

SELECT
p.product_id,
'SUN-COF-220',
'220g Candle',
'Coffee',
220,
'3" × 3.5" × 3.5"',
'white',
45,
599,
699,
true,
now(),
now(),
NULL::uuid,
NULL::uuid,
true
FROM products p
WHERE p.slug='coffee-soy-candle'

UNION ALL

SELECT
p.product_id,
'SUN-LAV-220',
'220g Candle',
'Lavender',
220,
'3" × 3.5" × 3.5"',
'white',
45,
599,
699,
true,
now(),
now(),
NULL::uuid,
NULL::uuid,
true
FROM products p
WHERE p.slug='lavender-soy-candle'

UNION ALL

SELECT
p.product_id,
'SUN-LEM-220',
'220g Candle',
'Lemongrass',
220,
'3" × 3.5" × 3.5"',
'white',
45,
599,
699,
true,
now(),
now(),
NULL::uuid,
NULL::uuid,
true
FROM products p
WHERE p.slug='lemongrass-soy-candle'

UNION ALL

SELECT
p.product_id,
'SUN-MAH-220',
'220g Candle',
'Mahogany Amber',
220,
'3" × 3.5" × 3.5"',
'white',
45,
599,
699,
true,
now(),
now(),
NULL::uuid,
NULL::uuid,
true
FROM products p
WHERE p.slug='mahogany-amber-soy-candle';