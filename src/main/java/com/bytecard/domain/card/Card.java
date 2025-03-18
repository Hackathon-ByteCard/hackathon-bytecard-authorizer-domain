package com.bytecard.domain.card;

import com.bytecard.domain.authorization.Authorization;
import com.bytecard.domain.shared.errors.AuthorizerDomainException;
import com.bytecard.domain.shared.errors.BusinessError;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
public class Card {
    private final UUID pan;
    private BigDecimal availableLimit;
    private LocalDateTime blockedSince;

    protected Card(final UUID pan, final BigDecimal availableLimit, final LocalDateTime blockedSince) {
        this.pan = pan;
        this.availableLimit = availableLimit;
        this.blockedSince = blockedSince;
    }

    protected void pay(final Authorization authorization) {
        validateBlockingRules();
        validateAvailableLimit(authorization);

        this.availableLimit = this.availableLimit.subtract(authorization.getAmount());
    }

    private void validateBlockingRules() {
        if(Objects.nonNull(this.blockedSince)) {
            throw new AuthorizerDomainException(BusinessError.BLOCKED_CARD, "card is blocked");
        }
    }

    private void validateAvailableLimit(final Authorization authorization) {
        if(Objects.isNull(authorization)) {
            throw new AuthorizerDomainException(BusinessError.INVARIANT_CONSTRAINT_ERROR, "authorization should not be null");
        }

        if(this.availableLimit.compareTo(authorization.getAmount()) < 0) {
            throw new AuthorizerDomainException(BusinessError.INSUFFICIENT_BALANCE, "available limit smaller than the authorization amount");
        }
    }

    protected void addToAvailableLimit(BigDecimal paymentAmount) {
        validatePaymentAmount(paymentAmount);
        this.availableLimit = this.availableLimit.add(paymentAmount);
    }

    private void validatePaymentAmount(final BigDecimal paymentAmount) {
        if(Objects.isNull(paymentAmount)) {
            throw new AuthorizerDomainException(BusinessError.INVARIANT_CONSTRAINT_ERROR, "payment amount should not be null");
        }

        if(paymentAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new AuthorizerDomainException(BusinessError.INVALID_PAYMENT_AMOUNT, "payment amount should not be less or equal to zero");
        }
    }

    protected void block() {
        this.blockedSince = LocalDateTime.now();
    }
}
