CREATE TABLE product_audit_log (
    id UUID PRIMARY KEY,
    product_id UUID NOT NULL,
    action VARCHAR(50) NOT NULL,
    performed_by UUID,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_product_audit_product
ON product_audit_log(product_id);

CREATE INDEX idx_product_audit_action
ON product_audit_log(action);

CREATE INDEX idx_product_audit_created_at
ON product_audit_log(created_at);
