package com.bytecard.domain.shared.bus.events;

import com.bytecard.domain.shared.errors.AuthorizerDomainException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class ErrorEvent extends DomainEvent {
    private final AuthorizerDomainException exception;
}
