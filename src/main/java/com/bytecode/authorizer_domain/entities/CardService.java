package com.bytecode.authorizer_domain.entities;

import com.bytecode.authorizer_domain.entities.vos.Authorization;
import com.bytecode.authorizer_domain.repositories.AuthorizationRepository;
import com.bytecode.authorizer_domain.repositories.CardRepository;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class CardService {
    private final AuthorizationRepository authorizationRepository;
    private final CardRepository cardRepository;

    public Authorization authorize(final Card card, final Authorization authorization) {
        card.pay(authorization);

        this.authorizationRepository.save(authorization);
        this.cardRepository.save(card);

        return authorization;
    }

    public void restoreLimit(final Card card, final BigDecimal paymentAmount) {
        card.addToAvailableLimit(paymentAmount);
        this.cardRepository.save(card);
    }
}
