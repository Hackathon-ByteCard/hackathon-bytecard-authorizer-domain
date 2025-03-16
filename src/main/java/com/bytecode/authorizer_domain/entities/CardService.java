package com.bytecode.authorizer_domain.entities;

import com.bytecode.authorizer_domain.entities.vos.Authorization;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CardService {
    public Authorization authorize(final Card card, final BigDecimal amount, final String description, final LocalDateTime time) {
        var authorization = new Authorization(card, amount, description, time);
        card.authorize(authorization);
        return authorization;
    }
}
