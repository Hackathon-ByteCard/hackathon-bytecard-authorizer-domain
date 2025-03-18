package com.bytecode.authorizer_domain.authorization;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Authorization {
    private final BigDecimal amount;
    private final String description;
    private final LocalDateTime time;
    private final UUID code;

    protected Authorization(final BigDecimal amount,
                            final String description,
                            final LocalDateTime time,
                            final UUID code) {
        this.amount = amount;
        this.description = description;
        this.time = time;
        this.code = code;
    }
}
