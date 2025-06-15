CREATE TABLE public."card" (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    account_id UUID NOT NULL UNIQUE CONSTRAINT fk_account_user REFERENCES public.account(id),
    number VARCHAR(16) UNIQUE NOT NULL,
    "limit" DECIMAL(19, 4) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    payment_day INTEGER NOT NULL,
    active BOOLEAN DEFAULT TRUE
);

CREATE INDEX idx_account_id ON public.card(account_id);