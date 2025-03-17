package com.bytecode.authorizer_domain.entities.vos;

import com.bytecode.authorizer_domain.errors.AuthorizerDomainException;
import com.bytecode.authorizer_domain.errors.BusinessError;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public record Authorization(
        BigDecimal amount,
        String description,
        LocalDateTime time
) {
    public Authorization {
        validateAuthorizationAmount(amount);
        validateDescription(description);
        validateTime(time);
    }

    private void validateAuthorizationAmount(final BigDecimal amount) {
        if(Objects.isNull(amount)) {
            throw new AuthorizerDomainException(BusinessError.INVARIANT_CONSTRAINT_ERROR, "amount should not be null");
        }

        if(amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw  new AuthorizerDomainException(BusinessError.INVALID_AUTHORIZATION_AMOUNT, "amount should be greater than zero");
        }
    }

    private void validateDescription(final String description) {
        if(Objects.isNull(description)) {
            throw new AuthorizerDomainException(BusinessError.INVARIANT_CONSTRAINT_ERROR, "description should not be null");
        }

        if(description.isBlank()) {
            throw new AuthorizerDomainException(BusinessError.INVALID_AUTHORIZATION_DESCRIPTION, "authorization should not be null, empty or blank");
        }
    }

    private void validateTime(final LocalDateTime time) {
        if(Objects.isNull(time)) {
            throw new AuthorizerDomainException(BusinessError.INVARIANT_CONSTRAINT_ERROR, "time should not be null");
        }

        if(time.isAfter(LocalDateTime.now())) {
            throw new AuthorizerDomainException(BusinessError.INVALID_AUTHORIZATION_TIME, "time should no tbe in the future");
        }
    }
}
