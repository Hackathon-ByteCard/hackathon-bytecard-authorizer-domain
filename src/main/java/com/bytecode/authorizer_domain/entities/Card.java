package com.bytecode.authorizer_domain.entities;

import com.bytecode.authorizer_domain.entities.vos.Authorization;
import com.bytecode.authorizer_domain.entities.vos.Cancellation;
import com.bytecode.authorizer_domain.errors.AuthorizerDomainException;
import com.bytecode.authorizer_domain.errors.BusinessError;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Card {
    private final UUID pan;
    private BigDecimal availableLimit;
    private List<Cancellation> cancellations;
    private LocalDateTime blockedSince;

    protected Card(final UUID pan, final BigDecimal availableLimit, final List<Cancellation> cancellations, final LocalDateTime blockedSince) {
        this.pan = pan;
        this.availableLimit = availableLimit;
        this.cancellations = cancellations;
        this.blockedSince = blockedSince;
    }

    public void authorize(final Authorization authorization) {
        validateAuthorization(authorization);
        this.availableLimit = this.availableLimit.subtract(authorization.amount());
    }

    private void validateAuthorization(final Authorization authorization) {
        if(Objects.isNull(authorization)) {
            throw new AuthorizerDomainException(BusinessError.INVARIANT_CONSTRAINT_ERROR, "authorization should not be null");
        }

        if(this.availableLimit.compareTo(authorization.amount()) < 0) {
            throw new AuthorizerDomainException(BusinessError.UNSUFFICIENT_BALANCE, "available limit smaller than the authorization amount");
        }
    }
}
