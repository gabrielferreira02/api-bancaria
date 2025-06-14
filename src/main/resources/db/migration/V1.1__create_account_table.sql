CREATE TABLE public."account" (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL UNIQUE CONSTRAINTS fk_account_user REFERENCES public.user(id),
    account_number VARCHAR(30) NOT NULL UNIQUE,
    balance DECIMAL(19, 4) NOT NULL,
    daily_transfer_limit DECIMAL(19, 4) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT positive_balance CHECK (balance >= 0),
    CONSTRAINT positive_limit CHECK (daily_transfer_limit >= 0)
)

CREATE INDEX idx_account_user_id ON public.account(user_id);
CREATE INDEX idx_account_number ON public.account(account_number);