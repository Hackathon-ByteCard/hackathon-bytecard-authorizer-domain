package com.bytecode.authorizer_domain.entities;

import com.bytecode.authorizer_domain.entities.vos.Cancellation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CardFactory {
    public static Card create(final UUID pan, final BigDecimal totalLimit) {
        return new Card(pan, totalLimit, new ArrayList<>(), null);
    }

    public static Card create(final UUID pan, final BigDecimal availableLimit, final List<Cancellation> cancellations, final LocalDateTime blockedSince) {
        return new Card(pan, availableLimit, cancellations, blockedSince);
    }
}
