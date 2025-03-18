package com.bytecard.domain;

import com.bytecard.domain.authorization.Authorization;
import com.bytecard.domain.authorization.AuthorizationService;
import com.bytecard.domain.card.Card;
import com.bytecard.domain.card.CardService;
import com.bytecard.domain.conciliation.ConciliationService;
import com.bytecard.domain.shared.bus.EventPublisher;
import com.bytecard.domain.shared.bus.events.ErrorEvent;
import com.bytecard.domain.shared.errors.AuthorizerDomainException;
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
        try {
            this.authorizationService.authorize(card, authorization);
        } catch (Exception e) {
            throw handleException(e);
        }
    }

    public void conciliate(final Card card, final Authorization authorization) {
        try {
            this.conciliationService.conciliate(card, authorization);
        } catch (Exception e) {
            throw handleException(e);
        }
    }

    public void restoreLimit(final Card card, final BigDecimal paymentAmount) {
        try {
            this.cardService.restoreLimit(card, paymentAmount);
        } catch (Exception e) {
            throw handleException(e);
        }
    }

    public Optional<Authorization> findAuthorization(final UUID authorizationCode) {
        try {
            return this.authorizationService.findAuthorization(authorizationCode);
        } catch (Exception e) {
            throw handleException(e);
        }
    }

    public Optional<Card> findCard(final UUID pan) {
        try {
            return this.cardService.findCard(pan);
        } catch (Exception e) {
            throw handleException(e);
        }
    }

    private AuthorizerDomainException handleException(Exception e) {
        var authorizerDomainException = AuthorizerDomainException.fromException(e);
        var event = new ErrorEvent(authorizerDomainException);
        EventPublisher.publish(event);
        return authorizerDomainException;
    }
}
