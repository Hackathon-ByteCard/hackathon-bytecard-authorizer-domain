package com.bytecode.authorizer_domain.entities;

import com.bytecode.authorizer_domain.errors.AuthorizerDomainException;
import com.bytecode.authorizer_domain.errors.BusinessError;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
public class Authorization {
    private final BigDecimal amount;
    private final String description;
    private final LocalDateTime time;
    private final UUID code;


    /// Create new authorization
    public Authorization(final BigDecimal amount,
                         final String description,
                         final LocalDateTime time) {
        validateNewAuthorization(amount, description, time);

        this.amount = amount;
        this.description = description;
        this.time = time;
        this.code = UUID.randomUUID();
    }

    ///  Deserialize authorization
    public Authorization(final BigDecimal amount,
                         final String description,
                         final LocalDateTime time,
                         final UUID code) {
        validateDeserialization(amount, description, time, code);

        this.amount = amount;
        this.description = description;
        this.time = time;
        this.code = code;
    }

    private void validateDeserialization(final BigDecimal amount,
                                         final String description,
                                         final LocalDateTime time,
                                         final UUID code) {
        validateNewAuthorization(amount, description, time);
        if(Objects.isNull(code)) {
            throw new AuthorizerDomainException(BusinessError.INVARIANT_CONSTRAINT_ERROR, "cannot deserialize with null code");
        }
    }

    private void validateNewAuthorization(final BigDecimal amount,
                                       final String description,
                                       final LocalDateTime time) {
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Authorization that = (Authorization) o;
        return Objects.equals(amount, that.amount) && Objects.equals(description, that.description) && Objects.equals(time, that.time) && Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, description, time, code);
    }
}
