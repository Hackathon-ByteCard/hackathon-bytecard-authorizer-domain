package com.bytecode.authorizer_domain.shared.errors;

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

    private AuthorizerDomainException(BusinessError businessError, String details, Exception e) {
        super(
                String.format(
                        "[%s] (%s) - %s",
                        LocalDateTime.now(),
                        businessError.getCode(),
                        details
                ),
                e
        );
        this.businessError = businessError;
    }

    public static AuthorizerDomainException fromException(Exception e) {
        AuthorizerDomainException authorizerDomainException;

        if(e instanceof AuthorizerDomainException domainException) {
            authorizerDomainException = domainException;
        } else {
            authorizerDomainException = new AuthorizerDomainException(
                    BusinessError.UNEXPECTED_ERROR,
                    "caught unexpected error",
                    e
            );
        }

        return authorizerDomainException;
    }
}
