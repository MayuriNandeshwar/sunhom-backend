-- 1. ATTRIBUTE DEFINITIONS
CREATE TABLE product_attributes (
    attribute_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    data_type VARCHAR(20) NOT NULL,
    is_filterable BOOLEAN NOT NULL DEFAULT FALSE,
    is_required BOOLEAN NOT NULL DEFAULT FALSE,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_product_attributes_active
    ON product_attributes (is_active);


-- 2. ENUM VALUES
CREATE TABLE product_attribute_values (
    value_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    attribute_id UUID NOT NULL,
    value VARCHAR(100) NOT NULL,
    display_order INT DEFAULT 0,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,

    CONSTRAINT fk_pav_attribute
        FOREIGN KEY (attribute_id)
        REFERENCES product_attributes(attribute_id)
        ON DELETE CASCADE,

    CONSTRAINT uq_attribute_value
        UNIQUE (attribute_id, value)
);

CREATE INDEX idx_pav_attribute
    ON product_attribute_values (attribute_id, is_active);


-- 3. ENTITY ASSIGNMENTS
CREATE TABLE product_entity_attributes (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    product_id UUID,
    variant_id UUID,
    attribute_id UUID NOT NULL,
    value_text VARCHAR(255),
    value_number DECIMAL(10,2),
    value_boolean BOOLEAN,
    value_enum UUID,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_pea_product
        FOREIGN KEY (product_id)
        REFERENCES products(product_id)
        ON DELETE CASCADE,

    CONSTRAINT fk_pea_variant
        FOREIGN KEY (variant_id)
        REFERENCES product_variants(variant_id)
        ON DELETE CASCADE,

    CONSTRAINT fk_pea_attribute
        FOREIGN KEY (attribute_id)
        REFERENCES product_attributes(attribute_id)
        ON DELETE CASCADE,

    CONSTRAINT fk_pea_enum_value
        FOREIGN KEY (value_enum)
        REFERENCES product_attribute_values(value_id),

    CONSTRAINT chk_product_or_variant
        CHECK (
            (product_id IS NOT NULL AND variant_id IS NULL)
         OR (product_id IS NULL AND variant_id IS NOT NULL)
        )
);

CREATE INDEX idx_pea_product
    ON product_entity_attributes (product_id);

CREATE INDEX idx_pea_variant
    ON product_entity_attributes (variant_id);

CREATE INDEX idx_pea_attribute
    ON product_entity_attributes (attribute_id);
