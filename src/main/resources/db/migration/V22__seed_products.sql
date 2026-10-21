INSERT INTO products (
sku_base,
product_name,
slug,
short_description,
product_description,
brand,
search_keywords,
is_active,
is_featured,
is_manual_bestseller,
created_at,
updated_at,
deleted_at,
created_by,
updated_by,
product_type_id,
category_id,
seo_title,
seo_description,
seo_keywords,
is_auto_bestseller,
is_new_arrival,
launch_at,
new_arrival_position
)

SELECT
'SUN-CIN-220',
'Cinnamon Vanilla Soy Candle',
'cinnamon-vanilla-soy-candle',
'Calm cozy warmth',
'A comforting blend of warm cinnamon and smooth vanilla that instantly softens the atmosphere of any room. Hand-poured using natural soy wax and premium fragrance oils, this candle offers a clean and consistent burn with a rich aromatic throw. Perfect for relaxing evenings, reading sessions, or cozy gatherings at home.',
'SUNHOM',
'cinnamon candle, vanilla candle, cozy candle, warm fragrance candle',
true,
true,
true,
now(),
now(),
NULL::timestamptz,
'00000000-0000-0000-0000-000000000000'::uuid,
'00000000-0000-0000-0000-000000000000'::uuid,
pt.product_type_id,
c.category_id,
'Cinnamon Vanilla Soy Candle | Warm Cozy Aroma Candle',
'Experience the comforting warmth of cinnamon blended with creamy vanilla in this premium soy candle crafted for cozy and relaxing spaces.',
'cinnamon candle, vanilla fragrance candle, cozy home candle',
false,
false,
now(),
NULL::integer
FROM product_types pt
JOIN categories c ON c.slug = 'signature-candle-collection'
WHERE pt.product_type_name = 'Soy Candle'


UNION ALL


SELECT
'SUN-COF-220',
'Coffee Soy Candle',
'coffee-soy-candle',
'Gentle awakening aroma',
'A rich roasted coffee aroma designed to energize your mornings and inspire productivity throughout the day.',
'SUNHOM',
'coffee candle, espresso candle, energizing fragrance candle',
true,
true,
true,
now(),
now(),
NULL::timestamptz,
'00000000-0000-0000-0000-000000000000'::uuid,
'00000000-0000-0000-0000-000000000000'::uuid,
pt.product_type_id,
c.category_id,
'Coffee Soy Candle | Fresh Brew Aroma Candle',
'Awaken your senses with the warm aroma of roasted coffee in this handcrafted soy candle.',
'coffee scented candle, espresso fragrance candle',
false,
false,
now(),
NULL::integer
FROM product_types pt
JOIN categories c ON c.slug = 'signature-candle-collection'
WHERE pt.product_type_name = 'Soy Candle'


UNION ALL


SELECT
'SUN-LAV-220',
'Lavender Soy Candle',
'lavender-soy-candle',
'Peaceful calming scent',
'A soothing lavender fragrance designed to calm the mind and promote deep relaxation.',
'SUNHOM',
'lavender candle, relaxing candle, sleep candle',
true,
true,
true,
now(),
now(),
NULL::timestamptz,
'00000000-0000-0000-0000-000000000000'::uuid,
'00000000-0000-0000-0000-000000000000'::uuid,
pt.product_type_id,
c.category_id,
'Lavender Soy Candle | Relaxing Aromatherapy Candle',
'Create a peaceful environment with this calming lavender scented soy candle.',
'lavender aromatherapy candle, sleep candle',
false,
false,
now(),
NULL::integer
FROM product_types pt
JOIN categories c ON c.slug = 'signature-candle-collection'
WHERE pt.product_type_name = 'Soy Candle'


UNION ALL


SELECT
'SUN-LEM-220',
'Lemongrass Soy Candle',
'lemongrass-soy-candle',
'Fresh uplifting energy',
'A vibrant lemongrass fragrance that refreshes your surroundings and lifts the mood instantly.',
'SUNHOM',
'lemongrass candle, citrus candle, refreshing candle',
true,
true,
true,
now(),
now(),
NULL::timestamptz,
'00000000-0000-0000-0000-000000000000'::uuid,
'00000000-0000-0000-0000-000000000000'::uuid,
pt.product_type_id,
c.category_id,
'Lemongrass Soy Candle | Fresh Citrus Aroma Candle',
'Refresh your space with the uplifting citrus aroma of lemongrass.',
'lemongrass scented candle, citrus aroma candle',
false,
false,
now(),
NULL::integer
FROM product_types pt
JOIN categories c ON c.slug = 'signature-candle-collection'
WHERE pt.product_type_name = 'Soy Candle'


UNION ALL


SELECT
'SUN-MAH-220',
'Mahogany Amber Soy Candle',
'mahogany-amber-soy-candle',
'Earthy luxury fragrance',
'A deep and luxurious blend of warm amber and rich mahogany that creates a sophisticated atmosphere.',
'SUNHOM',
'mahogany candle, amber candle, luxury candle',
true,
true,
true,
now(),
now(),
NULL::timestamptz,
'00000000-0000-0000-0000-000000000000'::uuid,
'00000000-0000-0000-0000-000000000000'::uuid,
pt.product_type_id,
c.category_id,
'Mahogany Amber Soy Candle | Luxury Earthy Aroma',
'Indulge in the rich warmth of mahogany and amber with this premium handcrafted soy candle.',
'mahogany amber candle, luxury scented candle',
false,
false,
now(),
NULL::integer
FROM product_types pt
JOIN categories c ON c.slug = 'signature-candle-collection'
WHERE pt.product_type_name = 'Soy Candle';