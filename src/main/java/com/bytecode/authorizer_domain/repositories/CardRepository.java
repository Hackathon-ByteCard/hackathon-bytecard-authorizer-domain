package com.bytecode.authorizer_domain.repositories;

import com.bytecode.authorizer_domain.entities.Card;

import java.util.UUID;

public interface CardRepository {
    void save(Card card);
    Card findOne(UUID pan);
}
