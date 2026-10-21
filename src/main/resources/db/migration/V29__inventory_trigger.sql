CREATE OR REPLACE FUNCTION create_inventory_for_variant()
RETURNS trigger AS $$
BEGIN

INSERT INTO inventory
(
variant_id,
available_quantity,
reserved_quantity,
low_stock_threshold,
updated_at
)
VALUES
(
NEW.variant_id,
0,
0,
10,
NOW()
);

RETURN NEW;

END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER trigger_create_inventory
AFTER INSERT ON product_variants
FOR EACH ROW
EXECUTE FUNCTION create_inventory_for_variant();