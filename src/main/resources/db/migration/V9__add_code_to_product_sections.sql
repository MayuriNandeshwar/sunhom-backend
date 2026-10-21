ALTER TABLE product_sections
ADD COLUMN code VARCHAR(100) NOT NULL;

-- Optional but recommended
CREATE UNIQUE INDEX uk_product_sections_code
ON product_sections(code);
