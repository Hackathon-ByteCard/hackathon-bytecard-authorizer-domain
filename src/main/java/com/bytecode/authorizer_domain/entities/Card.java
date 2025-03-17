package com.bytecode.authorizer_domain.entities;

import com.bytecode.authorizer_domain.errors.AuthorizerDomainException;
import com.bytecode.authorizer_domain.errors.BusinessError;
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

    public Card(final UUID pan, final BigDecimal availableLimit, final LocalDateTime blockedSince) {
        validateCard(pan, availableLimit, blockedSince);

        this.pan = pan;
        this.availableLimit = availableLimit;
        this.blockedSince = blockedSince;
    }

    private void validateCard(final UUID pan, final BigDecimal availableLimit, final LocalDateTime blockedSince) {
        if(Objects.isNull(pan)) {
            throw new AuthorizerDomainException(BusinessError.INVARIANT_CONSTRAINT_ERROR, "pan should not be null");
        }

        if(Objects.isNull(availableLimit)) {
            throw new AuthorizerDomainException(BusinessError.INVARIANT_CONSTRAINT_ERROR, "available limit should not be null");
        }

        if(availableLimit.compareTo(BigDecimal.ZERO) < 0) {
            throw new AuthorizerDomainException(BusinessError.INVALID_AVAILABLE_LIMIT, "available limit should not be less than zero");
        }

        if(Objects.nonNull(blockedSince) && blockedSince.isAfter(LocalDateTime.now())) {
            throw new AuthorizerDomainException(BusinessError.INVARIANT_CONSTRAINT_ERROR, "card cannot be blocked since the future");
        }
    }

    public void pay(final Authorization authorization) {
        validateBlockingRules();
        validateAvailableLimit(authorization);

        this.availableLimit = this.availableLimit.subtract(authorization.amount());
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

        if(this.availableLimit.compareTo(authorization.amount()) < 0) {
            throw new AuthorizerDomainException(BusinessError.INSUFFICIENT_BALANCE, "available limit smaller than the authorization amount");
        }
    }

    public void addToAvailableLimit(BigDecimal paymentAmount) {
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

    public void block() {
        this.blockedSince = LocalDateTime.now();
    }

    public void unblock() {
        this.blockedSince = null;
    }
}
