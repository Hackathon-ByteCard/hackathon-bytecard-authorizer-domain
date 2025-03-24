package com.bytecard.domain.authorization;

import com.bytecard.domain.shared.errors.AuthorizerDomainException;
import com.bytecard.domain.shared.errors.BusinessError;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class AuthorizationSpecification {
    protected static void satisfyOrThrow(final Authorization authorization) {
        if(Objects.isNull(authorization)) {
            throw new AuthorizerDomainException(BusinessError.INVARIANT_CONSTRAINT_ERROR, "authorization cannot be null");
        }

        validateAuthorization(authorization);
    }

    private static void validateAuthorization(final Authorization authorization) {
        validateRequiredFields(authorization);
        validateAuthorizationAmount(authorization);
        validateDescription(authorization);
        validateTime(authorization);
    }

    private static void validateRequiredFields(final Authorization authorization) {
        if (Objects.isNull(authorization.getCode())) {
            throw new AuthorizerDomainException(BusinessError.INVARIANT_CONSTRAINT_ERROR, "code must not be null");
        }

        if(Objects.isNull(authorization.getPan())) {
            throw new AuthorizerDomainException(BusinessError.INVARIANT_CONSTRAINT_ERROR, "pan must not be null");
        }

        if(Objects.isNull(authorization.getStatus())) {
            throw new AuthorizerDomainException(BusinessError.INVARIANT_CONSTRAINT_ERROR, "status must no be null");
        }
    }

    private static void validateAuthorizationAmount(final Authorization authorization) {
        if (Objects.isNull(authorization.getAmount())) {
            throw new AuthorizerDomainException(BusinessError.INVARIANT_CONSTRAINT_ERROR, "amount should not be null");
        }

        if (authorization.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new AuthorizerDomainException(BusinessError.INVALID_AUTHORIZATION_AMOUNT, "amount should be greater than zero");
        }
    }

    private static void validateDescription(final Authorization authorization) {
        if (Objects.isNull(authorization.getDescription())) {
            throw new AuthorizerDomainException(BusinessError.INVARIANT_CONSTRAINT_ERROR, "description should not be null");
        }

        if (authorization.getDescription().isBlank()) {
            throw new AuthorizerDomainException(BusinessError.INVALID_AUTHORIZATION_DESCRIPTION, "authorization should not be null, empty or blank");
        }
    }

    private static void validateTime(final Authorization authorization) {
        if (Objects.isNull(authorization.getTime())) {
            throw new AuthorizerDomainException(BusinessError.INVARIANT_CONSTRAINT_ERROR, "time should not be null");
        }

        if (authorization.getTime().isAfter(LocalDateTime.now())) {
            throw new AuthorizerDomainException(BusinessError.INVALID_AUTHORIZATION_TIME, "time should no tbe in the future");
        }
    }
}
