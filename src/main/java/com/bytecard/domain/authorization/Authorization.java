package com.bytecard.domain.authorization;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Authorization {
    private final BigDecimal amount;
    private final String description;
    private final LocalDateTime time;
    private final AuthorizationStatus status;
    private final UUID code;
    private final UUID pan;

    protected Authorization(final BigDecimal amount,
                            final String description,
                            final LocalDateTime time,
                            final AuthorizationStatus status,
                            final UUID code,
                            final UUID pan) {
        this.amount = amount;
        this.description = description;
        this.time = time;
        this.status = status;
        this.code = code;
        this.pan = pan;

        AuthorizationSpecification.satisfyOrThrow(this);
    }
}
