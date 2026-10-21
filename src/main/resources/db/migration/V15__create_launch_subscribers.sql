CREATE TABLE launch_subscribers (
    id UUID PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    source VARCHAR(50) DEFAULT 'launch_animation',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_launch_subscribers_email
ON launch_subscribers(email);
CREATE INDEX idx_launch_subscribers_created_at
ON launch_subscribers(created_at);