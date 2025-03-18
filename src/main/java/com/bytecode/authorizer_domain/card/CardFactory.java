package com.bytecode.authorizer_domain.card;

import com.bytecode.authorizer_domain.shared.errors.AuthorizerDomainException;
import com.bytecode.authorizer_domain.shared.errors.BusinessError;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class CardFactory {
    public static Card newCard(final BigDecimal totalLimit) {
        validateAvailableLimit(totalLimit);
        return new Card(UUID.randomUUID(), totalLimit, null);
    }

    public static Card deserialize(final UUID pan, final BigDecimal availableLimit, final LocalDateTime blockedSince) {
        validateCard(pan, availableLimit, blockedSince);
        return new Card(pan, availableLimit,blockedSince);
    }

    private static void validateCard(final UUID pan, final BigDecimal availableLimit, final LocalDateTime blockedSince) {
        validateAvailableLimit(availableLimit);

        if(Objects.isNull(pan)) {
            throw new AuthorizerDomainException(BusinessError.INVARIANT_CONSTRAINT_ERROR, "pan should not be null");
        }

        if(Objects.nonNull(blockedSince) && blockedSince.isAfter(LocalDateTime.now())) {
            throw new AuthorizerDomainException(BusinessError.INVARIANT_CONSTRAINT_ERROR, "card cannot be blocked since the future");
        }
    }

    private static void validateAvailableLimit(final BigDecimal availableLimit) {
        if(Objects.isNull(availableLimit)) {
            throw new AuthorizerDomainException(BusinessError.INVARIANT_CONSTRAINT_ERROR, "available limit should not be null");
        }

        if(availableLimit.compareTo(BigDecimal.ZERO) < 0) {
            throw new AuthorizerDomainException(BusinessError.INVALID_AVAILABLE_LIMIT, "available limit should not be less than zero");
        }
    }
}
