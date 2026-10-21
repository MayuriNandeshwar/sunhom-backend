INSERT INTO product_images
(
product_id,
variant_id,
product_image_url,
alt_text,
position,
media_type,
is_primary,
created_at,
is_hover_image,
width,
height,
url_thumbnail,
url_medium,
url_large
)

SELECT
p.product_id,
v.variant_id,
img.url,
img.alt,
img.position,
'IMAGE',
img.is_primary,
now(),
img.is_hover,
1200,
1200,

REPLACE(img.url,'/upload/','/upload/w_400,f_auto,q_auto/'),
REPLACE(img.url,'/upload/','/upload/w_800,f_auto,q_auto/'),
REPLACE(img.url,'/upload/','/upload/w_1600,f_auto,q_auto/')

FROM products p
JOIN product_variants v 
ON v.product_id = p.product_id AND v.is_default = true

JOIN (
VALUES

-- CINNAMON
('cinnamon-vanilla-soy-candle','https://res.cloudinary.com/dtz1gpnge/image/upload/v1772941995/1_gvygar.png','Cinnamon Vanilla Candle Front',1,true,false),
('cinnamon-vanilla-soy-candle','https://res.cloudinary.com/dtz1gpnge/image/upload/v1772170454/Cinnamon_1_cbyk8q.png','Cinnamon Vanilla Candle Lifestyle',2,false,true),
('cinnamon-vanilla-soy-candle','https://res.cloudinary.com/dtz1gpnge/image/upload/v1772893028/cinnamon2_fog95i.png','Cinnamon Vanilla Candle Side',3,false,false),
('cinnamon-vanilla-soy-candle','https://res.cloudinary.com/dtz1gpnge/image/upload/v1772889355/cinnamon_e8tskr.png','Cinnamon Vanilla Candle Label',4,false,false),
('cinnamon-vanilla-soy-candle','https://res.cloudinary.com/dtz1gpnge/image/upload/v1772893043/cinnamon1_qtvdsp.png','Cinnamon Vanilla Candle Detail',5,false,false),
('cinnamon-vanilla-soy-candle','https://res.cloudinary.com/dtz1gpnge/image/upload/v1772941942/1_lozlck.png','Cinnamon Vanilla Candle Packaging',6,false,false),

-- COFFEE
('coffee-soy-candle','https://res.cloudinary.com/dtz1gpnge/image/upload/v1772942007/4_ynbbm4.png','Coffee Candle Front',1,true,false),
('coffee-soy-candle','https://res.cloudinary.com/dtz1gpnge/image/upload/v1772170499/caramel_1_ex72uy.png','Coffee Candle Lifestyle',2,false,true),
('coffee-soy-candle','https://res.cloudinary.com/dtz1gpnge/image/upload/v1772893037/coffee2_opkpre.png','Coffee Candle Side',3,false,false),
('coffee-soy-candle','https://res.cloudinary.com/dtz1gpnge/image/upload/v1772889355/coffee_vczxut.png','Coffee Candle Label',4,false,false),
('coffee-soy-candle','https://res.cloudinary.com/dtz1gpnge/image/upload/v1772893023/coffee1_pjjksc.png','Coffee Candle Detail',5,false,false),
('coffee-soy-candle','https://res.cloudinary.com/dtz1gpnge/image/upload/v1772941942/5_lxvddq.png','Coffee Candle Packaging',6,false,false),

-- LAVENDER
('lavender-soy-candle','https://res.cloudinary.com/dtz1gpnge/image/upload/v1772942007/5_vjmeat.png','Lavender Candle Front',1,true,false),
('lavender-soy-candle','https://res.cloudinary.com/dtz1gpnge/image/upload/v1772170535/Lavender_Haze_1_u6jcfw.png','Lavender Candle Lifestyle',2,false,true),
('lavender-soy-candle','https://res.cloudinary.com/dtz1gpnge/image/upload/v1772893042/lavender2_aeruru.png','Lavender Candle Side',3,false,false),
('lavender-soy-candle','https://res.cloudinary.com/dtz1gpnge/image/upload/v1772889355/lavender_eaahwo.png','Lavender Candle Label',4,false,false),
('lavender-soy-candle','https://res.cloudinary.com/dtz1gpnge/image/upload/v1772893038/lavender1_xdkgc6.png','Lavender Candle Detail',5,false,false),
('lavender-soy-candle','https://res.cloudinary.com/dtz1gpnge/image/upload/v1772941940/4_betc3c.png','Lavender Candle Packaging',6,false,false),

-- LEMONGRASS
('lemongrass-soy-candle','https://res.cloudinary.com/dtz1gpnge/image/upload/v1772942010/2_kbmewq.png','Lemongrass Candle Front',1,true,false),
('lemongrass-soy-candle','https://res.cloudinary.com/dtz1gpnge/image/upload/v1772170412/Lime_Whisper_xjctob.png','Lemongrass Candle Lifestyle',2,false,true),
('lemongrass-soy-candle','https://res.cloudinary.com/dtz1gpnge/image/upload/v1772893040/lemon2_y9k2ga.png','Lemongrass Candle Side',3,false,false),
('lemongrass-soy-candle','https://res.cloudinary.com/dtz1gpnge/image/upload/v1772889353/lemongrass_qy0ud6.png','Lemongrass Candle Label',4,false,false),
('lemongrass-soy-candle','https://res.cloudinary.com/dtz1gpnge/image/upload/v1772893034/lemongrass1_hjz8bv.png','Lemongrass Candle Detail',5,false,false),
('lemongrass-soy-candle','https://res.cloudinary.com/dtz1gpnge/image/upload/v1772941938/2_z4kyiy.png','Lemongrass Candle Packaging',6,false,false),

-- MAHOGANY
('mahogany-amber-soy-candle','https://res.cloudinary.com/dtz1gpnge/image/upload/v1772942005/3_ggkzmb.png','Mahogany Amber Candle Front',1,true,false),
('mahogany-amber-soy-candle','https://res.cloudinary.com/dtz1gpnge/image/upload/v1772170348/Mahogany_Amber_uas2aj.png','Mahogany Amber Candle Lifestyle',2,false,true),
('mahogany-amber-soy-candle','https://res.cloudinary.com/dtz1gpnge/image/upload/v1772893041/mahogany2_z8khkb.png','Mahogany Amber Candle Side',3,false,false),
('mahogany-amber-soy-candle','https://res.cloudinary.com/dtz1gpnge/image/upload/v1772889354/mahogany_ftpw2o.png','Mahogany Amber Candle Label',4,false,false),
('mahogany-amber-soy-candle','https://res.cloudinary.com/dtz1gpnge/image/upload/v1772893044/mahogany1_rnttqj.png','Mahogany Amber Candle Detail',5,false,false),
('mahogany-amber-soy-candle','https://res.cloudinary.com/dtz1gpnge/image/upload/v1772941942/3_bxwy8z.png','Mahogany Amber Candle Packaging',6,false,false)

) AS img(slug,url,alt,position,is_primary,is_hover)

ON img.slug = p.slug;