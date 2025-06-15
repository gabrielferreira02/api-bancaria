CREATE TABLE public."transaction" (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    amount DECIMAL(19, 4) NOT NULL CHECK(amount > 0),
    transfer_fee DECIMAL(19, 4) NOT NULL CHECK(transfer_fee >= 0),
    fee_amount DECIMAL(19, 4) NOT NULL CHECK(fee_amount >= 0),
    type VARCHAR(50) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    sender_id UUID NOT NULL CONSTRAINT fk_account_sender REFERENCES public.account(id),
    receiver_id UUID NOT NULL CONSTRAINT fk_account_receiver REFERENCES public.account(id),
    CONSTRAINT no_self_transfer CHECK (sender_id != receiver_id)
);

CREATE INDEX idx_sender_id ON public.transaction(sender_id);
CREATE INDEX idx_receiver_id ON public.transaction(receiver_id);
CREATE INDEX idx_type ON public.transaction(type);
CREATE INDEX idx_created_at ON public.transaction(created_at);
