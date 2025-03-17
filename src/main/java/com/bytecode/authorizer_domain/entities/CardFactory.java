package com.bytecode.authorizer_domain.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class CardFactory {
    public static Card create(final UUID pan, final BigDecimal totalLimit) {
        return new Card(pan, totalLimit, null);
    }

    public static Card create(final UUID pan, final BigDecimal availableLimit, final LocalDateTime blockedSince) {
        return new Card(pan, availableLimit, blockedSince);
    }
}
