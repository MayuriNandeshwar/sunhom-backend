ALTER TABLE launch_subscribers
RENAME TO newsletter_subscribers;

ALTER TABLE newsletter_subscribers
ADD COLUMN user_id UUID NULL;

ALTER TABLE newsletter_subscribers
ALTER COLUMN source SET DEFAULT 'footer_newsletter';