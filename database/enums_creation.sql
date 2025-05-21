CREATE TYPE operation_type AS ENUM ('transfer', 'payment');
CREATE TYPE operation_status AS ENUM ('cancelled', 'processing', 'successful');
