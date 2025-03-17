package com.bytecode.authorizer_domain.events.impls;

import com.bytecode.authorizer_domain.errors.AuthorizerDomainException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class ErrorEvent extends DomainEvent {
    private final AuthorizerDomainException exception;
}
