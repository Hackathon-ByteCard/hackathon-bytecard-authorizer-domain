package com.bytecode.authorizer_domain.shared.bus.events;

import com.bytecode.authorizer_domain.shared.errors.AuthorizerDomainException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class ErrorEvent extends DomainEvent {
    private final AuthorizerDomainException exception;
}
