package com.bytecard.domain.conciliation;

import com.bytecard.domain.authorization.Authorization;
import com.bytecard.domain.card.Card;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Cancellation{
    private final Card card;
    private final Authorization originAuthorization;
    private final LocalDateTime canceledAt;

    protected Cancellation(final Card card,
                           final Authorization authorization,
                           final LocalDateTime canceledAt) {
        this.card = card;
        this.originAuthorization = authorization;
        this.canceledAt = canceledAt;
    }
}
