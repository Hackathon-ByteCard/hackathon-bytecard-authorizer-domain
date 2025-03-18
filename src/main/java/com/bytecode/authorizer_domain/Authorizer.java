package com.bytecode.authorizer_domain;

import com.bytecode.authorizer_domain.authorization.Authorization;
import com.bytecode.authorizer_domain.authorization.AuthorizationService;
import com.bytecode.authorizer_domain.card.Card;
import com.bytecode.authorizer_domain.card.CardService;
import com.bytecode.authorizer_domain.conciliation.ConciliationService;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class Authorizer {
    private final AuthorizationService authorizationService;
    private final CardService cardService;
    private final ConciliationService conciliationService;

    public void authorize(final Card card, final Authorization authorization) {
        this.authorizationService.authorize(card, authorization);
    }

    public void conciliate(final Card card, final Authorization authorization) {
        this.conciliationService.conciliate(card, authorization);
    }

    public void restoreLimit(final Card card, final BigDecimal paymentAmount) {
        this.cardService.restoreLimit(card, paymentAmount);
    }

    public Optional<Authorization> findAuthorization(final UUID authorizationCode) {
        return this.authorizationService.findAuthorization(authorizationCode);
    }

    public Optional<Card> findCard(final UUID pan) {
        return this.cardService.findCard(pan);
    }
}
