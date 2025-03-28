package com.bytecard.domain.conciliation;

import com.bytecard.domain.card.Card;

import java.time.LocalDateTime;

public interface CancellationRepository {
    void save(Cancellation cancellation);
    int count(Card card, LocalDateTime since);
}
