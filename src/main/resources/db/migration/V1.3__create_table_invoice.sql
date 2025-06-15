CREATE TABLE public."invoice" (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    card_id UUID NOT NULL CONSTRAINT fk_card_user REFERENCES public.card(id),
    total_amount DECIMAL(19, 4) NOT NULL CHECK (total_amount >= 0),
    start_date TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    end_date TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    due_date TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(50) NOT NULL
);

CREATE INDEX idx_card_id ON public.invoice(card_id);