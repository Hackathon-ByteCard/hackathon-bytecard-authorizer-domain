package com.bytecode.authorizer_domain.authorization;

import com.bytecode.authorizer_domain.shared.errors.AuthorizerDomainException;
import com.bytecode.authorizer_domain.shared.errors.BusinessError;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class AuthorizationFactory {
    public static Authorization newAuthorization(final BigDecimal amount,
                                                 final String description,
                                                 final LocalDateTime time) {
        var code = UUID.randomUUID();
        validateAuthorization(amount, description, time, code);
        return new Authorization(amount, description, time, UUID.randomUUID());
    }

    public static Authorization create(final BigDecimal amount,
                                            final String description,
                                            final LocalDateTime time,
                                            final UUID code) {
        validateAuthorization(amount, description, time, code);
        return new Authorization(amount, description, time, code);
    }

    private static void validateAuthorization(final BigDecimal amount,
                                         final String description,
                                         final LocalDateTime time,
                                         final UUID code) {
        if (Objects.isNull(code)) {
            throw new AuthorizerDomainException(BusinessError.INVARIANT_CONSTRAINT_ERROR, "cannot deserialize with null code");
        }
        validateAuthorizationAmount(amount);
        validateDescription(description);
        validateTime(time);
    }

    private static void validateAuthorizationAmount(final BigDecimal amount) {
        if (Objects.isNull(amount)) {
            throw new AuthorizerDomainException(BusinessError.INVARIANT_CONSTRAINT_ERROR, "amount should not be null");
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new AuthorizerDomainException(BusinessError.INVALID_AUTHORIZATION_AMOUNT, "amount should be greater than zero");
        }
    }

    private static void validateDescription(final String description) {
        if (Objects.isNull(description)) {
            throw new AuthorizerDomainException(BusinessError.INVARIANT_CONSTRAINT_ERROR, "description should not be null");
        }

        if (description.isBlank()) {
            throw new AuthorizerDomainException(BusinessError.INVALID_AUTHORIZATION_DESCRIPTION, "authorization should not be null, empty or blank");
        }
    }

    private static void validateTime(final LocalDateTime time) {
        if (Objects.isNull(time)) {
            throw new AuthorizerDomainException(BusinessError.INVARIANT_CONSTRAINT_ERROR, "time should not be null");
        }

        if (time.isAfter(LocalDateTime.now())) {
            throw new AuthorizerDomainException(BusinessError.INVALID_AUTHORIZATION_TIME, "time should no tbe in the future");
        }
    }
}
