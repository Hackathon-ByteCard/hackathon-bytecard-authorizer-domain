package com.bytecode.authorizer_domain.events;

import com.bytecode.authorizer_domain.entities.vos.Authorization;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class ClearingEvent extends DomainEvent {
    final public Authorization authorization;
}
