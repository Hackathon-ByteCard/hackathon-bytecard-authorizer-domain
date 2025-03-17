package com.bytecode.authorizer_domain.services;

import com.bytecode.authorizer_domain.entities.Authorization;
import com.bytecode.authorizer_domain.entities.Card;
import com.bytecode.authorizer_domain.errors.AuthorizerDomainException;
import com.bytecode.authorizer_domain.repositories.AuthorizationRepository;
import com.bytecode.authorizer_domain.repositories.CardRepository;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class CardService {
    private final AuthorizationRepository authorizationRepository;
    private final CardRepository cardRepository;

    public Authorization authorize(final Card card, final Authorization authorization) {
        try {
            card.pay(authorization);

            this.authorizationRepository.save(authorization);
            this.cardRepository.save(card);

            return authorization;
        } catch (Exception e) {
            throw AuthorizerDomainException.fromException(e);
        }
    }

    public void restoreLimit(final Card card, final BigDecimal paymentAmount) {
        try {
            card.addToAvailableLimit(paymentAmount);
            this.cardRepository.save(card);
        } catch (Exception e) {
            throw AuthorizerDomainException.fromException(e);
        }
    }
}
