package com.bytecode.authorizer_domain.repositories;

import com.bytecode.authorizer_domain.entities.Card;
import com.bytecode.authorizer_domain.entities.Cancellation;

import java.time.LocalDateTime;

public interface CancellationRepository {
    void save(Cancellation cancellation);
    int count(Card card, LocalDateTime since);
}
