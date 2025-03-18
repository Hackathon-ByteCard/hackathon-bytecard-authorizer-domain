package com.bytecode.authorizer_domain.conciliation;

import com.bytecode.authorizer_domain.card.Card;

import java.time.LocalDateTime;

public interface CancellationRepository {
    void save(Cancellation cancellation);
    int count(Card card, LocalDateTime since);
}
