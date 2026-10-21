INSERT INTO public.roles (role_id, role_name, description, created_at) VALUES ('3f87a6d2-51c3-4e89-b7f2-1d5b3a4c5e6f', 'CUSTOMER', 'Customer user account', now()) ON CONFLICT (role_name) DO NOTHING;
