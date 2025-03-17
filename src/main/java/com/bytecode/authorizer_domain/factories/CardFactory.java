package com.bytecode.authorizer_domain.factories;

import com.bytecode.authorizer_domain.entities.Card;

import java.math.BigDecimal;
import java.util.UUID;

public class CardFactory {
    public static Card newCard(final BigDecimal totalLimit) {
        return new Card(
                UUID.randomUUID(),
                totalLimit,
                null
        );
    }
}
