package com.bytecode.authorizer_domain.events.impls;

import com.bytecode.authorizer_domain.entities.Authorization;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class ClearingEvent extends DomainEvent {
    final public Authorization authorization;
}
