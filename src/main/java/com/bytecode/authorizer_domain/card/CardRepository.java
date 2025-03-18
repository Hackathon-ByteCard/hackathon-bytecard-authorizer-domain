package com.bytecode.authorizer_domain.card;

import java.util.Optional;
import java.util.UUID;

public interface CardRepository {
    void save(Card card);
    Optional<Card> findOne(UUID pan);
}
