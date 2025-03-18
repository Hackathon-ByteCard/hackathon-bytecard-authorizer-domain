package com.bytecode.authorizer_domain.conciliation;

import com.bytecode.authorizer_domain.authorization.Authorization;
import com.bytecode.authorizer_domain.card.Card;
import com.bytecode.authorizer_domain.shared.errors.AuthorizerDomainException;
import com.bytecode.authorizer_domain.shared.errors.BusinessError;

import java.time.LocalDateTime;
import java.util.Objects;

public class CancellationFactory {
    public static Cancellation newCancellation(final Card card, final Authorization authorization) {
        var now = LocalDateTime.now();
        validateCancellation(card, authorization, now);
        return new Cancellation(card, authorization, now);
    }

    public static Cancellation create(final Card card, final Authorization authorization, final LocalDateTime time) {
        validateCancellation(card, authorization, time);
        return new Cancellation(card, authorization, time);
    }

    private static void validateCancellation(final Card card, final Authorization authorization, final LocalDateTime time) {
        if(Objects.isNull(card)) {
            throw new AuthorizerDomainException(BusinessError.INVARIANT_CONSTRAINT_ERROR, "card must not be null");
        }

        if(Objects.isNull(authorization)) {
            throw new AuthorizerDomainException(BusinessError.INVARIANT_CONSTRAINT_ERROR, "authorization must not be null");
        }

        if(Objects.isNull(time)) {
            throw new AuthorizerDomainException(BusinessError.INVARIANT_CONSTRAINT_ERROR, "time must not be null");
        }

        if(time.isAfter(LocalDateTime.now())) {
            throw new AuthorizerDomainException(BusinessError.INVALID_CANCELLATION_TIME, "cancellation time cannot be in the future");
        }
    }
}
