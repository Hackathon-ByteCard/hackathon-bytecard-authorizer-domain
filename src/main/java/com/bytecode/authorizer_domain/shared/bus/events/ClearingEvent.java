package com.bytecode.authorizer_domain.shared.bus.events;

import com.bytecode.authorizer_domain.authorization.Authorization;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class ClearingEvent extends DomainEvent {
    final public Authorization authorization;
}
