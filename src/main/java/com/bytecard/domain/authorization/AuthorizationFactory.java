package com.bytecard.domain.authorization;

import com.bytecard.domain.shared.errors.AuthorizerDomainException;
import com.bytecard.domain.shared.errors.BusinessError;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class AuthorizationFactory {
    public static Authorization newAuthorization(final BigDecimal amount,
                                                 final String description,
                                                 final LocalDateTime time,
                                                 final UUID pan) {
        var code = UUID.randomUUID();
        return new Authorization(
                amount,
                description,
                time,
                AuthorizationStatus.PENDING,
                code,
                pan
        );
    }

    public static Authorization create(final BigDecimal amount,
                                       final String description,
                                       final LocalDateTime time,
                                       final AuthorizationStatus status,
                                       final UUID code,
                                       final UUID pan) {
        return new Authorization(amount, description, time, status, code, pan);
    }
}
