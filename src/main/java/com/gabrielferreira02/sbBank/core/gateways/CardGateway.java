package com.gabrielferreira02.sbBank.core.gateways;

import com.gabrielferreira02.sbBank.core.domain.Card;

public interface CardGateway {
    String generateNumber();
    Card createCard(Card card);
}
