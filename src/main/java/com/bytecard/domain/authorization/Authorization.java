package com.bytecard.domain.authorization;

import com.bytecard.domain.shared.errors.AuthorizerDomainException;
import com.bytecard.domain.shared.errors.BusinessError;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
public class Authorization {
    private BigDecimal amount;
    private String description;
    private LocalDateTime time;
    private AuthorizationStatus status;
    private UUID code;
    private UUID pan;

    protected Authorization(final BigDecimal amount,
                            final String description,
                            final LocalDateTime time,
                            final AuthorizationStatus status,
                            final UUID code,
                            final UUID pan) {
        setAmount(amount);
        setDescription(description);
        setTime(time);
        setStatus(status);
        setCode(code);
        setPan(pan);

    }

    private void setDescription(final String description) {
        if (Objects.isNull(description)) {
            throw new AuthorizerDomainException(BusinessError.INVARIANT_CONSTRAINT_ERROR, "description should not be null");
        }

        if (description.isBlank()) {
            throw new AuthorizerDomainException(BusinessError.INVALID_AUTHORIZATION_DESCRIPTION, "authorization should not be null, empty or blank");
        }

        this.description = description;
    }

    private void setAmount(final BigDecimal amount) {
        if (Objects.isNull(amount)) {
            throw new AuthorizerDomainException(BusinessError.INVARIANT_CONSTRAINT_ERROR, "amount should not be null");
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new AuthorizerDomainException(BusinessError.INVALID_AUTHORIZATION_AMOUNT, "amount should be greater than zero");
        }

        this.amount = amount;
    }

    private void setCode(final UUID code) {
        if (Objects.isNull(code)) {
            throw new AuthorizerDomainException(BusinessError.INVARIANT_CONSTRAINT_ERROR, "code must not be null");
        }

        this.code = code;
    }

    private void setPan(final UUID pan) {
        if(Objects.isNull(pan)) {
            throw new AuthorizerDomainException(BusinessError.INVARIANT_CONSTRAINT_ERROR, "pan must not be null");
        }

        this.pan = pan;
    }

    private void setStatus(final AuthorizationStatus status) {
        if(Objects.isNull(status)) {
            throw new AuthorizerDomainException(BusinessError.INVARIANT_CONSTRAINT_ERROR, "status must no be null");
        }

        this.status = status;
    }

    private void setTime(final LocalDateTime time) {
        if (Objects.isNull(time)) {
            throw new AuthorizerDomainException(BusinessError.INVARIANT_CONSTRAINT_ERROR, "time should not be null");
        }

        if (time.isAfter(LocalDateTime.now())) {
            throw new AuthorizerDomainException(BusinessError.INVALID_AUTHORIZATION_TIME, "time should no tbe in the future");
        }

        this.time = time;
    }
}
