CREATE TABLE product_section_products (
    product_section_products_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    section_id UUID NOT NULL,
    product_id UUID NOT NULL,

    display_order INT NOT NULL DEFAULT 0,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_psp_section
        FOREIGN KEY (section_id)
        REFERENCES product_sections(section_id)
        ON DELETE CASCADE,

    CONSTRAINT fk_psp_product
        FOREIGN KEY (product_id)
        REFERENCES products(product_id)
        ON DELETE CASCADE,

    CONSTRAINT uk_section_product UNIQUE (section_id, product_id)
);


-- Fast section → products lookup
CREATE INDEX idx_psp_section_id
    ON product_section_products(section_id);

-- Fast product → sections lookup
CREATE INDEX idx_psp_product_id
    ON product_section_products(product_id);

-- Ordering + active filtering (homepage critical)
CREATE INDEX idx_psp_section_active_order
    ON product_section_products(section_id, is_active, display_order);
