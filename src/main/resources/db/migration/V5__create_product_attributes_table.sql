CREATE TABLE product_attributes (
    attribute_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    product_id UUID NOT NULL,
    attribute_key VARCHAR(100) NOT NULL,
    attribute_value TEXT NOT NULL,
    display_order INT DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_attr_product
        FOREIGN KEY (product_id)
        REFERENCES products(product_id)
        ON DELETE CASCADE
);

CREATE INDEX idx_product_attributes_product
ON product_attributes (product_id);

CREATE INDEX idx_product_attributes_product_order
ON product_attributes (product_id, display_order);
