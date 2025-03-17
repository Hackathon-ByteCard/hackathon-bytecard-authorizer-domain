package com.bytecode.authorizer_domain.factories;

import com.bytecode.authorizer_domain.entities.Authorization;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class AuthorizationFactory {
    public static Authorization newAuthorization(final BigDecimal amount,
                                                 final String description,
                                                 final LocalDateTime time) {
        return new Authorization(amount, description, time, UUID.randomUUID());
    }
}
