package com.bytecode.authorizer_domain.errors;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AuthorizerDomainException extends RuntimeException {
    private final BusinessError businessError;

    public AuthorizerDomainException(BusinessError businessError, String details) {
        super(
                String.format(
                        "[%s] (%s) - %s",
                        LocalDateTime.now(),
                        businessError.getCode(),
                        details
                )
        );
        this.businessError = businessError;
    }
}
